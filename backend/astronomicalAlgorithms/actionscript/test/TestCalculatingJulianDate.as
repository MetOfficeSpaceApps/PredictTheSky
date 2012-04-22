package gov.nasa.predictthesky.astronomical
{
	import gov.nasa.predictthesky.JulianDateCalculator;
	
	import org.flexunit.assertThat;
	import org.flexunit.asserts.fail;
	import org.hamcrest.number.closeTo;

	public class TestCalculatingJulianDate
	{		
		[Before]
		public function setUp():void
		{
		}
		
		[After]
		public function tearDown():void
		{
		}
		
		[BeforeClass]
		public static function setUpBeforeClass():void
		{
		}
		
		[AfterClass]
		public static function tearDownAfterClass():void
		{
		}
		
		[Test]
		public function testConvertingGregorianToJulianDate():void {
			// Comparison value calculated at http://aa.usno.navy.mil/data/docs/JulianDate.php
			var utcDate:Date = new Date(Date.UTC(2012,3,22,8));
			assertThat(JulianDateCalculator.convertToJulian(utcDate), closeTo(2456039.833333, 0.0001));
			
		}
		
		[Test]
		public function testCalculatingGreenwichMeanSiderealTime():void {
			var utcDate:Date = new Date(Date.UTC(2012, 3, 22, 8));
			assertThat(calculateGreenwichMeanSiderealTime(utcDate), closeTo(14.0299, 0.001))
		}
		
		private function calculateGreenwichMeanSiderealTime(utcDate:Date):Object
		{
			var dateOfLastMidnight:Date = new Date(Date.UTC(utcDate.fullYearUTC, utcDate.monthUTC, utcDate.dateUTC));
			var hoursSinceMidnight:Number = utcDate.hoursUTC;
			var JD0:Number = JulianDateCalculator.convertToJulian(dateOfLastMidnight);
			trace("Julian date of last midnight ", JD0);
			var D0:Number = JD0 - 2451545.0;
			var T:Number = 0;
			var GMST:Number = (6.697374558 + (0.06570982441908 * D0) + (1.00273790935 * hoursSinceMidnight) + (0.000026 * T * T)) % 24;
			
			return GMST;
		}		

		
	}
}