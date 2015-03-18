package com.meghaditya.sax.calendar;

import java.util.Arrays;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

public class CalendarUtilities {
	// Time related convenience constants, in milliseconds
	static final int SECONDS = 1000;
	static final int MINUTES = SECONDS * 60;
	static final int HOURS = MINUTES * 60;
	static final long DAYS = HOURS * 24;

	static final int sCurrentYear = new GregorianCalendar().get(Calendar.YEAR);
	static final TimeZone sGmtTimeZone = TimeZone.getTimeZone("GMT");

	// We want to find a time zone whose DST info is accurate to one minute
	static final int STANDARD_DST_PRECISION = MINUTES;
	// If we can't find one, we'll try a more lenient standard (this is better
	// than guessing a
	// time zone, which is what we otherwise do). Note that this specifically
	// addresses an issue
	// seen in some time zones sent by MS Exchange in which the start and end
	// hour differ
	// for no apparent reason
	static final int LENIENT_DST_PRECISION = 4 * HOURS;

	// The following constants relate to standard Microsoft data sizes
	// For documentation, see
	// http://msdn.microsoft.com/en-us/library/aa505945.aspx
	static final int MSFT_LONG_SIZE = 4;
	static final int MSFT_WCHAR_SIZE = 2;
	static final int MSFT_WORD_SIZE = 2;

	// The following constants relate to Microsoft's SYSTEMTIME structure
	// For documentation, see:
	// http://msdn.microsoft.com/en-us/library/ms724950(VS.85).aspx?ppud=4

	static final int MSFT_SYSTEMTIME_YEAR = 0 * MSFT_WORD_SIZE;
	static final int MSFT_SYSTEMTIME_MONTH = 1 * MSFT_WORD_SIZE;
	static final int MSFT_SYSTEMTIME_DAY_OF_WEEK = 2 * MSFT_WORD_SIZE;
	static final int MSFT_SYSTEMTIME_DAY = 3 * MSFT_WORD_SIZE;
	static final int MSFT_SYSTEMTIME_HOUR = 4 * MSFT_WORD_SIZE;
	static final int MSFT_SYSTEMTIME_MINUTE = 5 * MSFT_WORD_SIZE;
	// static final int MSFT_SYSTEMTIME_SECONDS = 6 * MSFT_WORD_SIZE;
	// static final int MSFT_SYSTEMTIME_MILLIS = 7 * MSFT_WORD_SIZE;
	static final int MSFT_SYSTEMTIME_SIZE = 8 * MSFT_WORD_SIZE;

	// The following constants relate to Microsoft's TIME_ZONE_INFORMATION
	// structure
	// For documentation, see
	// http://msdn.microsoft.com/en-us/library/ms725481(VS.85).aspx
	static final int MSFT_TIME_ZONE_BIAS_OFFSET = 0;
	static final int MSFT_TIME_ZONE_STANDARD_NAME_OFFSET = MSFT_TIME_ZONE_BIAS_OFFSET
			+ MSFT_LONG_SIZE;
	static final int MSFT_TIME_ZONE_STANDARD_DATE_OFFSET = MSFT_TIME_ZONE_STANDARD_NAME_OFFSET
			+ (MSFT_WCHAR_SIZE * 32);
	static final int MSFT_TIME_ZONE_STANDARD_BIAS_OFFSET = MSFT_TIME_ZONE_STANDARD_DATE_OFFSET
			+ MSFT_SYSTEMTIME_SIZE;
	static final int MSFT_TIME_ZONE_DAYLIGHT_NAME_OFFSET = MSFT_TIME_ZONE_STANDARD_BIAS_OFFSET
			+ MSFT_LONG_SIZE;
	static final int MSFT_TIME_ZONE_DAYLIGHT_DATE_OFFSET = MSFT_TIME_ZONE_DAYLIGHT_NAME_OFFSET
			+ (MSFT_WCHAR_SIZE * 32);
	static final int MSFT_TIME_ZONE_DAYLIGHT_BIAS_OFFSET = MSFT_TIME_ZONE_DAYLIGHT_DATE_OFFSET
			+ MSFT_SYSTEMTIME_SIZE;
	static final int MSFT_TIME_ZONE_SIZE = MSFT_TIME_ZONE_DAYLIGHT_BIAS_OFFSET
			+ MSFT_LONG_SIZE;

	// Internal structure for storing a time zone date from a SYSTEMTIME
	// structure
	// This date represents either the start or the end time for DST
	static class TimeZoneDate {
		String year;
		int month;
		int dayOfWeek;
		int day;
		int time;
		int hour;
		int minute;
	}

	/**
	 * Build a GregorianCalendar, based on a time zone and TimeZoneDate.
	 * 
	 * @param timeZone
	 *            the time zone we're checking
	 * @param tzd
	 *            the TimeZoneDate we're interested in
	 * @return a GregorianCalendar with the given time zone and date
	 */
	static long getMillisAtTimeZoneDateTransition(TimeZone timeZone,
			TimeZoneDate tzd) {
		GregorianCalendar testCalendar = new GregorianCalendar(timeZone);
		testCalendar.set(GregorianCalendar.YEAR, sCurrentYear);
		testCalendar.set(GregorianCalendar.MONTH, tzd.month);
		testCalendar.set(GregorianCalendar.DAY_OF_WEEK, tzd.dayOfWeek);
		testCalendar.set(GregorianCalendar.DAY_OF_WEEK_IN_MONTH, tzd.day);
		testCalendar.set(GregorianCalendar.HOUR_OF_DAY, tzd.hour);
		testCalendar.set(GregorianCalendar.MINUTE, tzd.minute);
		testCalendar.set(GregorianCalendar.SECOND, 0);
		return testCalendar.getTimeInMillis();
	}

	// Return a 4-byte long from a byte array (little endian)
	static int getLong(byte[] bytes, int offset) {
		return (bytes[offset++] & 0xFF) | ((bytes[offset++] & 0xFF) << 8)
				| ((bytes[offset++] & 0xFF) << 16)
				| ((bytes[offset] & 0xFF) << 24);
	}

	// Return a 2-byte word from a byte array (little endian)
	static int getWord(byte[] bytes, int offset) {
		return (bytes[offset++] & 0xFF) | ((bytes[offset] & 0xFF) << 8);
	}

	// Build a TimeZoneDate structure from a SYSTEMTIME within a byte array at a
	// given offset
	static TimeZoneDate getTimeZoneDateFromSystemTime(byte[] bytes, int offset) {
		TimeZoneDate tzd = new TimeZoneDate();

		// MSFT year is an int; TimeZone is a String
		int num = getWord(bytes, offset + MSFT_SYSTEMTIME_YEAR);
		tzd.year = Integer.toString(num);

		// MSFT month = 0 means no daylight time
		// MSFT months are 1 based; TimeZone is 0 based
		num = getWord(bytes, offset + MSFT_SYSTEMTIME_MONTH);
		if (num == 0) {
			return null;
		} else {
			tzd.month = num - 1;
		}

		// MSFT day of week starts w/ Sunday = 0; TimeZone starts w/ Sunday = 1
		tzd.dayOfWeek = getWord(bytes, offset + MSFT_SYSTEMTIME_DAY_OF_WEEK) + 1;

		// Get the "day" in TimeZone format
		num = getWord(bytes, offset + MSFT_SYSTEMTIME_DAY);
		// 5 means "last" in MSFT land; for TimeZone, it's -1
		if (num == 5) {
			tzd.day = -1;
		} else {
			tzd.day = num;
		}

		// Turn hours/minutes into ms from midnight (per TimeZone)
		int hour = getWord(bytes, offset + MSFT_SYSTEMTIME_HOUR);
		tzd.hour = hour;
		int minute = getWord(bytes, offset + MSFT_SYSTEMTIME_MINUTE);
		tzd.minute = minute;
		tzd.time = (hour * HOURS) + (minute * MINUTES);

		return tzd;
	}

	static TimeZone easTzToTimeZoneImpl(String timeZoneString) {
		{
			int precision = MINUTES;
			TimeZone timeZone = null;
			// First, we need to decode the base64 string
			byte[] timeZoneBytes = Base64.getDecoder().decode(
					timeZoneString.getBytes());

			// Then, we get the bias (similar to a rawOffset); for TimeZone, we
			// need ms
			// but EAS gives us minutes, so do the conversion. Note that EAS is
			// the bias that's added
			// to the time zone to reach UTC; our library uses the time from UTC
			// to our time zone, so
			// we need to change the sign
			int bias = -1 * getLong(timeZoneBytes, MSFT_TIME_ZONE_BIAS_OFFSET)
					* MINUTES;

			// Get all of the time zones with the bias as a rawOffset; if there
			// aren't any, we return
			// the default time zone
			String[] zoneIds = TimeZone.getAvailableIDs(bias);
			if (zoneIds.length > 0) {
				// Try to find an existing TimeZone from the data provided by
				// EAS
				// We start by pulling out the date that standard time begins
				TimeZoneDate dstEnd = getTimeZoneDateFromSystemTime(
						timeZoneBytes, MSFT_TIME_ZONE_STANDARD_DATE_OFFSET);
				if (dstEnd == null) {
					// If the default time zone is a match
					TimeZone defaultTimeZone = TimeZone.getDefault();
					if (!defaultTimeZone.useDaylightTime()
							&& Arrays.asList(zoneIds).contains(
									defaultTimeZone.getID())) {
						System.err
								.println("TimeZone without DST found to be default: "
										+ defaultTimeZone.getID());
						return defaultTimeZone;
					}
					// In this case, there is no daylight savings time, so the
					// only interesting data
					// for possible matches is the offset and DST availability;
					// we'll take the first
					// match for those
					for (String zoneId : zoneIds) {
						timeZone = TimeZone.getTimeZone(zoneId);
						if (!timeZone.useDaylightTime()) {
							System.err
									.println("TimeZone without DST found by offset: "
											+ timeZone.getID());
							return timeZone;
						}
					}
					// None found, return null
					return null;
				} else {
					TimeZoneDate dstStart = getTimeZoneDateFromSystemTime(
							timeZoneBytes, MSFT_TIME_ZONE_DAYLIGHT_DATE_OFFSET);
					// See comment above for bias...
					long dstSavings = getLong(timeZoneBytes,
							MSFT_TIME_ZONE_DAYLIGHT_BIAS_OFFSET);
					dstSavings *= -1 * MINUTES;

					// We'll go through each time zone to find one with the same
					// DST transitions and
					// savings length
					for (String zoneId : zoneIds) {
						// Get the TimeZone using the zoneId
						timeZone = TimeZone.getTimeZone(zoneId);

						// Our strategy here is to check just before and just
						// after the transitions
						// and see whether the check for daylight time matches
						// the expectation
						// If both transitions match, then we have a match for
						// the offset and start/end
						// of dst. That's the best we can do for now, since
						// there's no other info
						// provided by EAS (i.e. we can't get dynamic
						// transitions, etc.)

						// Check one minute before and after DST start
						// transition
						long millisAtTransition = getMillisAtTimeZoneDateTransition(
								timeZone, dstStart);
						Date before = new Date(millisAtTransition - precision);
						Date after = new Date(millisAtTransition + precision);
						if (timeZone.inDaylightTime(before))
							continue;
						if (!timeZone.inDaylightTime(after))
							continue;

						// Check one minute before and after DST end transition
						millisAtTransition = getMillisAtTimeZoneDateTransition(
								timeZone, dstEnd);
						// Note that we need to subtract an extra hour here,
						// because we end up with
						// gaining an hour in the transition BACK to standard
						// time
						before = new Date(millisAtTransition
								- (dstSavings + precision));
						after = new Date(millisAtTransition + precision);
						if (!timeZone.inDaylightTime(before))
							continue;
						if (timeZone.inDaylightTime(after))
							continue;

						// Check that the savings are the same
						if (dstSavings != timeZone.getDSTSavings())
							continue;
						return timeZone;
					}
					// In this case, there is no daylight savings time, so the
					// only interesting data
					// is the offset, and we know that all of the zoneId's
					// match; we'll take the first
					boolean lenient = false;
					// if ((dstStart.hour != dstEnd.hour)
					//		&& (precision == STANDARD_DST_PRECISION)) {
					//	timeZone = easTzToTimeZoneImpl(timeZoneString);
					//	lenient = true;
					// } else {
						timeZone = TimeZone.getTimeZone(zoneIds[0]);
					// }
					System.err
							.println("No TimeZone with correct DST settings; using "
									+ (lenient ? "lenient" : "first")
									+ ": "
									+ timeZone.getID());
				}
				return timeZone;
			}
		}
		return null;
	}
}
