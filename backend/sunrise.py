#code comes from 
#http://michelanders.blogspot.co.uk/2010/12/calulating-sunrise-and-sunset-in-python.html
from math import cos,sin,acos,asin,tan  
from math import degrees as deg, radians as rad  
from datetime import date,datetime,time, timedelta, tzinfo
  
# this module is not provided here. See text.  
#from timezone import LocalTimezone  

ZERO = timedelta(0)
HOUR = timedelta(hours=1)

# A UTC class.

ZERO = timedelta(0)
HOUR = timedelta(hours=1)
            
class FixedOffset(tzinfo):
    """Fixed offset in minutes east from UTC."""

    def __init__(self, offset, name):
        self.__offset = timedelta(minutes=offset)
        self.__name = name

    def utcoffset(self, dt):
        return self.__offset

    def tzname(self, dt):
        return self.__name

    def dst(self, dt):
        return ZERO
        
class sun:  
    """  
    Calculate sunrise and sunset based on equations from NOAA 
        
 
    typical use, calculating the sunrise at the present day: 
  
    import datetime 
    import sunrise 
    s = sun(lat=49,long=3) 
    print('sunrise at ',s.sunrise(when=datetime.datetime.now()) 
    """  
    def __init__(self,lat=52.37,long=4.90): # default Amsterdam  
        self.lat=lat  
        self.long=long  
	
    def sunrise(self,when=None):  
        """ 
        return the time of sunrise as a datetime.time object 
        when is a datetime.datetime object. If none is given 
        a local time zone is assumed (including daylight saving 
        if present) 
        """  
        if when is None : 
            #when = datetime.now(tz=LocalTimezone())  
            when = datetime.now()  
        self.__preptime(when)  
        self.__calc()  
        return sun.__timefromdecimalday(self.sunrise_t)  
	
    def sunset(self,when=None):  
        if when is None : 
            #when = datetime.now(tz=LocalTimezone())
            when = datetime.now()
        self.__preptime(when)  
        self.__calc()  
        return sun.__timefromdecimalday(self.sunset_t)  
	
    def solarnoon(self,when=None):  
        if when is None : 
            #when = datetime.now(tz=LocalTimezone())  
            when = datetime.now()  
        self.__preptime(when)  
        self.__calc()  
        return sun.__timefromdecimalday(self.solarnoon_t)  
   
    def civiltwilightmorning(self, when = None):
        if when is None : 
            #when = datetime.now(tz=LocalTimezone())  
            when = datetime.now()  
        self.__preptime(when)  
        self.__calc()  
        return sun.__timefromdecimalday(self.ctstartmorning)  
    
    def civiltwilightevening(self, when = None):
        if when is None : 
            #when = datetime.now(tz=LocalTimezone())  
            when = datetime.now()  
        self.__preptime(when)  
        self.__calc()  
        return sun.__timefromdecimalday(self.ctendevening)  
        
    def astrotwilightmorning(self, when = None):
        if when is None : 
            #when = datetime.now(tz=LocalTimezone())  
            when = datetime.now()  
        self.__preptime(when)  
        self.__calc()  
        return sun.__timefromdecimalday(self.atstartmorning)  

    def astrotwilightevening(self, when = None):
        if when is None : 
            #when = datetime.now(tz=LocalTimezone())  
            when = datetime.now()  
        self.__preptime(when)  
        self.__calc()  
        return sun.__timefromdecimalday(self.atendevening)  
        
    def nightstart(self, when = None):
        if when is None : 
            #when = datetime.now(tz=LocalTimezone())  
            when = datetime.now()  
        self.__preptime(when)  
        self.__calc()  
        return sun.__timefromdecimalday(self.darkevening)  

    def nightend(self, when = None):
        if when is None : 
            #when = datetime.now(tz=LocalTimezone())  
            when = datetime.now()  
            self.__preptime(when)  
            self.__calc()  
        return sun.__timefromdecimalday(self.darkmorning)  
    
    @staticmethod  
    def __timefromdecimalday(day):  
        """ 
        returns a datetime.time object. 
       
        day is a decimal day between 0.0 and 1.0, e.g. noon = 0.5 
        """  
        hours  = 24.0*day  
        h      = int(hours)  
        minutes= (hours-h)*60  
        m      = int(minutes)  
        seconds= (minutes-m)*60  
        s      = int(seconds)  
        return time(hour=h,minute=m,second=s)  
  
    def __preptime(self,when):  
        """ 
        Extract information in a suitable format from when,  
        a datetime.datetime object. 
        """  
        # datetime days are numbered in the Gregorian calendar  
        # while the calculations from NOAA are distibuted as  
        # OpenOffice spreadsheets with days numbered from  
        # 1/1/1900. The difference are those numbers taken for   
        # 18/12/2010  
        self.day = when.toordinal()-(734124-40529)  
        t=when.time()  
        self.time= (t.hour + t.minute/60.0 + t.second/3600.0)/24.0  
	
        self.timezone=0  
        offset=when.utcoffset()  
        if not offset is None:  
            self.timezone=offset.seconds/3600.0  
        
    def __calc(self):  
        """ 
        Perform the actual calculations for sunrise, sunset and 
        a number of related quantities. 
       
        The results are stored in the instance variables 
        sunrise_t, sunset_t and solarnoon_t 
        """  
        timezone = self.timezone # in hours, east is positive  
        longitude= self.long     # in decimal degrees, east is positive  
        latitude = self.lat      # in decimal degrees, north is positive  
  
        time  = self.time # percentage past midnight, i.e. noon  is 0.5  
        day      = self.day     # daynumber 1=1/1/1900  

        Jday     =day+2415018.5+time-timezone/24 # Julian day  
        Jcent    =(Jday-2451545)/36525    # Julian century  

        Manom    = 357.52911+Jcent*(35999.05029-0.0001537*Jcent)  
        Mlong    = 280.46646+Jcent*(36000.76983+Jcent*0.0003032)%360  
        Eccent   = 0.016708634-Jcent*(0.000042037+0.0001537*Jcent)  
        Mobliq   = 23+(26+((21.448-Jcent*(46.815+Jcent*(0.00059-Jcent*0.001813))))/60)/60  
        obliq    = Mobliq+0.00256*cos(rad(125.04-1934.136*Jcent))  
        vary     = tan(rad(obliq/2))*tan(rad(obliq/2))  
        Seqcent  = sin(rad(Manom))*(1.914602-Jcent*(0.004817+0.000014*Jcent))+sin(rad(2*Manom))*(0.019993-0.000101*Jcent)+sin(rad(3*Manom))*0.000289  
        Struelong= Mlong+Seqcent  
        Sapplong = Struelong-0.00569-0.00478*sin(rad(125.04-1934.136*Jcent))  
        declination = deg(asin(sin(rad(obliq))*sin(rad(Sapplong))))  

        eqtime   = 4*deg(vary*sin(2*rad(Mlong))-2*Eccent*sin(rad(Manom))+4*Eccent*vary*sin(rad(Manom))*cos(2*rad(Mlong))-0.5*vary*vary*sin(4*rad(Mlong))-1.25*Eccent*Eccent*sin(2*rad(Manom)))  

        hourangle= deg(acos(cos(rad(90.833))/(cos(rad(latitude))*cos(rad(declination)))-tan(rad(latitude))*tan(rad(declination))))  
        hourangle_ct= deg(acos(cos(rad(96))/(cos(rad(latitude))*cos(rad(declination)))-tan(rad(latitude))*tan(rad(declination))))  
        hourangle_at= deg(acos(cos(rad(102))/(cos(rad(latitude))*cos(rad(declination)))-tan(rad(latitude))*tan(rad(declination))))  
        hourangle_dark = deg(acos(cos(rad(108))/(cos(rad(latitude))*cos(rad(declination)))-tan(rad(latitude))*tan(rad(declination))))  
        #print hourangle
        self.solarnoon_t=(720-4*longitude-eqtime+timezone*60)/1440  
        #print self.solarnoon_t
        
        self.sunrise_t  =self.solarnoon_t-hourangle*4/1440  
        self.sunset_t   =self.solarnoon_t+hourangle*4/1440  
        self.ctstartmorning = self.solarnoon_t-hourangle_ct*4/1440
        self.ctendevening = self.solarnoon_t+hourangle_ct*4/1440
        self.atstartmorning =self.solarnoon_t-hourangle_at*4/1440
        self.atendevening = self.solarnoon_t+hourangle_at*4/1440
        self.darkmorning = self.solarnoon_t-hourangle_dark*4/1440
        self.darkevening = self.solarnoon_t+hourangle_dark*4/1440
def getsign(i1):
    if i1 >= 0.0:
        return 1.0
    return -1.0

def JulianDay (year, month, day, hour):
    '''
    COMPUTES THE JULIAN DATE (JD) GIVEN A GREGORIAN CALENDAR
    DATE (YEAR,MONTH,DAY).
    Taken from http://aa.usno.navy.mil/faq/docs/JD_Formula.php
    '''

    #I= float(year)
    #J= float(month)
    #K= float(day)
    #JD= K-32075+1461*(I+4800+(J-14)/12.0)/4+367*(J-2-(J-14.0)/12.0*12)  /12.0-3.0*((I+4900+(J-14)/12.0)/100.0)/4.0
    
    K=year
    M= month
    I = day
    UT = hour
    JD = 	367.0*K - float(int((7.0*(K+float(int((M+9)/12))))/4.0  ) ) + float(int((275*M)/9)) + I + 1721013.5 + UT/24.0 - 0.5 * getsign( 100.0*K+M-190002.5 ) + 0.5
    return JD

def GMST(year, month, day, hour):
    '''
    Calculate the greenwich approximate sidereal time
    Taken from http://aa.usno.navy.mil/faq/docs/GAST.php
    '''
    JD = JulianDay (year, month, day, hour)
    JD0 = JulianDay (year, month, day, 0)
    H = (JD-JD0)*24.0
    H1 = hour
    JD0 = JD - H/24.0
    D = jd1 - 2451545.0
    D0 = JD0 - 2451545.0 
    T = D/36525.0
    GMST = 6.697374558 + 0.06570982441908 * D0 + 1.00273790935 * H + 0.000026 * (T*T)
    omega= 125.04 - 0.052954 *D
    L = 280.47 + 0.98565 * D
    epsilon = 23.4393 - 0.0000004 *D
    delta_psi = -0.000319 * sin (omega) - 0.000024 * sin (2*L)
    eqeq = delta_psi * cos(epsilon) 
    GMST = GMST%24.0
    GAST = (GMST) + eqeq
    GAST = GAST % 24.0
    #GMST = 18.697374558 + 24.06570982441908 * D
    return [GMST, GAST]
    
def altitudeAndAzimuth(year, month, day, hour, lat, long, acension, declination):
    '''
    THIS DOESN"T WORK YET!!!
    Taken from http://aa.usno.navy.mil/faq/docs/Alt_Az.php
    '''
    [gmst, gast] = GMST(year, month, day, hour)
    alpha = acension
    lambda1 = long
    delta = declination
    phi = latitude
    
    LHA_plus = (gast - alpha)*15.0 + lambda1
    LHA_minus = (gast - alpha)*15.0 - lambda1
    
    altitude = asin(cos(LHA_plus)* cos(delta) * cos(phi)+sin(delta)*sin(phi) )
    azimuth = atan ( -sin(LHA_plus)/(tan(delta)*cos(phi) - sin(phi) * cos(LHA_plus)) )
    return [altitude,azimuth]
    
if __name__ == "__main__":  
    exeterLatitude = 50.7   
    exeterLongitude =  -3.4
    s=sun(lat=exeterLatitude,long=exeterLongitude)  
    tz1 = FixedOffset(60, 'British Summer Time')    
    timenow = datetime.now(tz1)
    jd1 = JulianDay(timenow.year, timenow.month, timenow.day, timenow.hour )    
    K=timenow.year
    M= timenow.month
    I = timenow.day
    UT = timenow.hour
    [gmst,gast] = GMST(timenow.year, timenow.month, timenow.day, timenow.hour )
    #[altitude, azimuth] = altitudeAndAzimuth(year, month, day, hour, exeterLatitude, exeterLongitude, acension, declination)
    print 'Julian Day is ' + str(jd1)
    print 'GMST is ' + str(gmst)
    print 'Current time is ' + str(timenow)
    noontime = s.solarnoon(timenow)
    print 'sunrise at ' + str (s.sunrise(timenow)) + 'sunset at' + str(s.sunset(timenow))  
    print 'civil twilight starts at ' + str(s.civiltwilightmorning(timenow)) + 'in the morning and ends at ' + str(s.civiltwilightevening(timenow)) + 'in the evening'
    print 'astronomical twilight starts at ' + str(s.astrotwilightmorning(timenow)) + 'in the morning and ends at ' + str(s.astrotwilightevening(timenow)) + 'in the evening'
    print 'full darkness ends at ' + str(s.nightend(timenow)) + ' in the morning and starts at ' + str(s.nightstart(timenow)) + 'in the evening'
    #test