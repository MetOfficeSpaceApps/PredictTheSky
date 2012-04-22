package gov.nasa.predictthesky
{
	public class JulianDateCalculator
	{
		public function JulianDateCalculator()
		{
		}
		
		/**
		 * Convert date to Julian date.  Uses calculation on Wikipedia
		 * @param date Date to convert to Julian
		 * @returns Julian date
		 */
		public static function convertToJulian(date:Date):Number {
			var month:Number = date.monthUTC + 1;
			var year:Number = date.fullYearUTC;
			var day:Number = date.dateUTC;
			var hour:Number = date.hoursUTC;
			var minute:Number = date.minutesUTC;
			var second:Number = date.secondsUTC;
			
			var a:Number = (14 - month)/12;
			var y:Number = year + 4800 - a;
			var m:Number = month + (12 * a) - 3;
			var julianDayNumber:Number = day 
				+ Math.floor(((153 * m) + 2)/5) 
				+ (365 * y)
				+ Math.floor(y/4)
				- Math.floor(y/100)
				+ Math.floor(y/400)
				- 32045;
			
			
			var julianTime:Number = julianDayNumber 
				+ Math.floor((hour - 12)/24) 
				+ Math.floor(minute/1440)
				+ Math.floor(second/86400);
			
			return julianTime;

		}
	}
}