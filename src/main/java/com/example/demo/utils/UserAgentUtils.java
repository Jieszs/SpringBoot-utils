package com.example.demo.utils;

import eu.bitwalker.useragentutils.*;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * UserAgent工具类
 *
 *         <dependency>
 *             <groupId>eu.bitwalker</groupId>
 *             <artifactId>UserAgentUtils</artifactId>
 *             <version>1.21</version>
 *         </dependency>
 *
 */
public class UserAgentUtils {

    /**
     * 获取浏览器
     *
     * @param request
     * @return
     */
    public static String getBrowser(HttpServletRequest request) {
        String str = request.getHeader("User-Agent");
        if (StringUtils.isEmpty(str)) return null;
        UserAgent ua = UserAgent.parseUserAgentString(str);
        if (ua == null) return null;
        Browser browser = ua.getBrowser();
        if (browser == null) return null;
        return browser.getName();
    }

    /**
     * 获取浏览器版本
     *
     * @param request
     * @return
     */
    public static String getBrowserVersion(HttpServletRequest request) {
        String str = request.getHeader("User-Agent");
        if (StringUtils.isEmpty(str)) return null;
        UserAgent ua = UserAgent.parseUserAgentString(str);
        if (ua == null) return null;
        Version version = ua.getBrowserVersion();
        if (version == null) return null;
        return version.getVersion();
    }

    /**
     * 获取操作系统
     *
     * @param request
     * @return
     */
    public static String getOS(HttpServletRequest request) {
        String str = request.getHeader("User-Agent");
        if (StringUtils.isEmpty(str)) return null;
        UserAgent ua = UserAgent.parseUserAgentString(str);
        if (ua == null) return null;
        OperatingSystem operatingSystem = ua.getOperatingSystem();
        if (operatingSystem == null) return null;
        return operatingSystem.getName();
    }

    /**
     * 获取设备类型
     *
     * @param request
     * @return
     */
    public static String getDeviceType(HttpServletRequest request) {
        String str = request.getHeader("User-Agent");
        if (StringUtils.isEmpty(str)) return null;
        UserAgent ua = UserAgent.parseUserAgentString(str);
        if (ua == null) return null;
        OperatingSystem operatingSystem = ua.getOperatingSystem();
        if (operatingSystem == null) return null;
        DeviceType deviceType = operatingSystem.getDeviceType();
        if (deviceType == null) return null;
        return deviceType.getName();
    }

    /**
     * 判断是否来自微信
     *
     * @param request
     * @return
     */
    public static Boolean isVisitorFromWechat(HttpServletRequest request) {
        String str = request.getHeader("User-Agent");
        if (StringUtils.isEmpty(str)) return false;
        if (str.contains("micromessenger")) return true;
        return false;
    }
}
