package com.baolan2005.vietnameselunar

import java.util.*

class VietnameseCalendar {
    private var solarDate: Date
    private var solarCalendar: Calendar
    var vietnameseDate: VietnameseDate

    constructor(date: Date) {
        solarDate = date
        solarCalendar = Calendar.getInstance()
        solarCalendar.setTime(date)
        vietnameseDate = convertSolar2Lunar()
    }

    private fun convertSolar2Lunar(): VietnameseDate {
        val timeZone = 7
        val dayNumber: Int = jdFromDate(solarDate)
        val k = ((dayNumber - 2415021.076998695) / 29.530588853).toInt()
        var monthStart: Int = getNewMoonDay(k + 1, timeZone)
        if (monthStart > dayNumber) {
            monthStart = getNewMoonDay(k, timeZone)
        }
        val solarYear = solarCalendar[Calendar.YEAR]
        var lunarYear = 0
        var a11: Int = getLunarMonth11(solarYear, timeZone)
        var b11 = a11
        if (a11 >= monthStart) {
            lunarYear = solarYear
            a11 = getLunarMonth11(solarYear - 1, timeZone)
        } else {
            lunarYear = solarYear + 1
            b11 = getLunarMonth11(solarYear + 1, timeZone)
        }
        val lunarDay = dayNumber - monthStart + 1
        val diff = ((monthStart - a11) / 29)
        var lunarLeap = false
        var lunarMonth = diff + 11
        if (b11 - a11 > 365) {
            val leapMonthDiff: Int = getLeapMonthOffset(a11, timeZone)
            if (diff >= leapMonthDiff) {
                lunarMonth = diff + 10
                if (diff == leapMonthDiff) {
                    lunarLeap = true
                }
            }
        }
        if (lunarMonth > 12) {
            lunarMonth = lunarMonth - 12
        }
        if (lunarMonth >= 11 && diff < 4) {
            lunarYear -= 1
        }
        val chi = arrayOf(
            "Thân",
            "Dậu",
            "Tuất",
            "Hợi",
            "Tí",
            "Sửu",
            "Dần",
            "Mão",
            "Thìn",
            "Tỵ",
            "Ngọ",
            "Mùi"
        )
        val can = arrayOf("Canh", "Tân", "Nhâm", "Quý", "Giáp", "Ất", "Bính", "Đinh", "Mậu", "Kỷ")
        val congiap = chi[lunarYear % 12]
        val muoican = can[lunarYear % 10]

        val result = VietnameseDate(lunarDay, lunarMonth, muoican, congiap)

        return result
    }

    private fun jdFromDate(date: Date): Int {
        val calendar = Calendar.getInstance()
        calendar.time = date
        val day = calendar[Calendar.DAY_OF_MONTH]
        val month = calendar[Calendar.MONTH] + 1
        val year = calendar[Calendar.YEAR]
        val a = (14 - month) / 12
        val y = year + 4800 - a
        val m = month + 12 * a - 3
        var jd = day + (153 * m + 2) / 5 + 365 * y + y / 4 - y / 100 + y / 400 - 32045
        if (jd < 2299161) {
            jd = day + (153 * m + 2) / 5 + 365 * y + y / 4 - 32083
        }
        return jd
    }

    private fun getNewMoonDay(k: Int, timeZone: Int): Int {
        val T = k / 1236.85 // Time in Julian centuries from 1900 January 0.5
        val T2 = T * T
        val T3 = T2 * T
        val dr = Math.PI / 180
        var Jd1 = 2415020.75933 + 29.53058868 * k + 0.0001178 * T2 - 0.000000155 * T3
        Jd1 = Jd1 + 0.00033 * Math.sin((166.56 + 132.87 * T - 0.009173 * T2) * dr) // Mean new moon
        val M = 359.2242 + 29.10535608 * k - 0.0000333 * T2 - 0.00000347 * T3 // Sun's mean anomaly
        val Mpr =
            306.0253 + 385.81691806 * k + 0.0107306 * T2 + 0.00001236 * T3 // Moon's mean anomaly
        val F =
            21.2964 + 390.67050646 * k - 0.0016528 * T2 - 0.00000239 * T3 // Moon's argument of latitude
        var C1 = (0.1734 - 0.000393 * T) * Math.sin(M * dr) + 0.0021 * Math.sin(2 * dr * M)
        C1 = C1 - 0.4068 * Math.sin(Mpr * dr) + 0.0161 * Math.sin(dr * 2 * Mpr)
        C1 = C1 - 0.0004 * Math.sin(dr * 3 * Mpr)
        C1 = C1 + 0.0104 * Math.sin(dr * 2 * F) - 0.0051 * Math.sin(dr * (M + Mpr))
        C1 = C1 - 0.0074 * Math.sin(dr * (M - Mpr)) + 0.0004 * Math.sin(dr * (2 * F + M))
        C1 = C1 - 0.0004 * Math.sin(dr * (2 * F - M)) - 0.0006 * Math.sin(dr * (2 * F + Mpr))
        C1 = C1 + 0.0010 * Math.sin(dr * (2 * F - Mpr)) + 0.0005 * Math.sin(dr * (2 * Mpr + M))
        var deltat = 0.0
        deltat = if (T < -11) {
            0.001 + 0.000839 * T + 0.0002261 * T2 - 0.00000845 * T3 - 0.000000081 * T * T3
        } else {
            -0.000278 + 0.000265 * T + 0.000262 * T2
        }
        val JdNew = Jd1 + C1 - deltat
        return (JdNew + 0.5 + timeZone / 24.0).toInt()
    }

    private fun getLunarMonth11(year: Int, timeZone: Int): Int {
        val calendar = Calendar.getInstance()
        calendar[year, 12] = 31
        val off = jdFromDate(calendar.time) - 2415021
        val k = (off / 29.530588853).toInt()
        var nm = getNewMoonDay(k, timeZone)
        val sunLong: Int = getSunLongitude(nm, timeZone) // sun longitude at local midnight
        if (sunLong >= 9) {
            nm = getNewMoonDay(k - 1, timeZone)
        }
        return nm
    }

    private fun getSunLongitude(jdn: Int, timeZone: Int): Int {
        val T =
            (jdn - 2451545.5 - timeZone / 24) / 36525 // Time in Julian centuries from 2000-01-01 12:00:00 GMT
        val T2 = T * T
        val dr = Math.PI / 180 // degree to radian
        val M =
            357.52910 + 35999.05030 * T - 0.0001559 * T2 - 0.00000048 * T * T2 // mean anomaly, degree
        val L0 = 280.46645 + 36000.76983 * T + 0.0003032 * T2 // mean longitude, degree
        var DL = (1.914600 - 0.004817 * T - 0.000014 * T2) * Math.sin(dr * M)
        DL = DL + (0.019993 - 0.000101 * T) * Math.sin(dr * 2 * M) + 0.000290 * Math.sin(dr * 3 * M)
        var L = L0 + DL // true longitude, degree
        L = L * dr
        L = L - Math.PI * 2 * (L / (Math.PI * 2)).toInt() // Normalize to (0, 2*PI)
        return (L / Math.PI * 6).toInt()
    }

    private fun getLeapMonthOffset(a11: Int, timeZone: Int): Int {
        val k = ((a11 - 2415021.076998695) / 29.530588853 + 0.5).toInt()
        var last = 0
        var i = 1 // We start with the month following lunar month 11
        var arc = getSunLongitude(getNewMoonDay(k + i, timeZone), timeZone)
        do {
            last = arc
            i++
            arc = getSunLongitude(getNewMoonDay(k + i, timeZone), timeZone)
        } while (arc != last && i < 14)
        return i - 1
    }

}

class VietnameseDate {
    var day = 1
    var month = 1
    var year = ""
    var can = ""
    var chi = ""

    constructor(day: Int, month: Int, can: String, chi: String) {
        this.day = day
        this.month = month
        this.year = "%s %s".format(can, chi)
        this.can = can
        this.chi = chi
    }

    override fun toString(): String {
        var result = ""
        var strNgay = "Ngày %d".format(day)
        if (day < 10) {
            strNgay = "Mùng %d".format(day)
        }
        var strThang = "tháng %d".format(month)
        if (month == 1) {
            strThang = "tháng Giêng";
        }
        else if (month == 12) {
            strThang = "tháng Chạp";
        }

        if (month == 1 && day < 4) {
            result = "%s Tết %s".format(strNgay, year)
        }
        else {
            result = "%s, %s, năm %s".format(strNgay, strThang, year)
        }
        return result
    }
}