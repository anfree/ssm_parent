package org.zeng.util;


import org.apache.commons.lang3.StringUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 自定义字符串工具类
 * Created by 曾祥江 on 2018/7/31.
 * zengxiangjiang@umpay.com
 */
public class StringUtil {

    private static final char SEPARATOR = '_';

    private static final Pattern NUMBER_PATTERN = Pattern.compile("[0-9]*");

    public static String trim(Object src) {
        return src == null ? "" : src.toString().trim();
    }

    /**
     * 验证参数集不能为空
     *
     * @param objects 参数集
     * @return true:空；false:非空
     */
    public static Boolean isEmpty(Object... objects) {
        if (objects == null || objects.length == 0) {
            return true;
        }
        for (Object object : objects) {
            String trimmed = trim(object);
            if (trimmed.isEmpty()) {
                return true;
            }
        }
        return false;
    }

    /**
     * 校验数字
     *
     * @param obj 需要校验的参数
     * @return true:是数字；false:不是数字
     */
    public static boolean isNumeric(Object obj) {
        Matcher match = NUMBER_PATTERN.matcher(String.valueOf(obj));
        return match.matches();
    }

    /**
     * 生成指定位数的随机数
     */
    public static Integer random(Integer n) {
        String maxStr = "9";
        String minStr = "1";
        for (int i = 1; i < n; i++) {
            maxStr += 9;
            minStr += 0;
        }
        int max = Integer.valueOf(maxStr);
        int min = Integer.valueOf(minStr);
        Random random = new Random();
        int s = random.nextInt(max) % (max - min + 1) + min;
        return s;
    }

    public static String[] split(String s, String delimiter) {
        if (s == null) {
            return null;
        } else {
            int stringLength = s.length();
            int delimiterLength;
            if (delimiter != null && (delimiterLength = delimiter.length()) != 0) {
                int count = 0;

                int start;
                int end;
                for (start = 0; (end = s.indexOf(delimiter, start)) != -1; start = end + delimiterLength) {
                    ++count;
                }

                ++count;
                String[] result = new String[count];
                count = 0;

                for (start = 0; (end = s.indexOf(delimiter, start)) != -1; start = end + delimiterLength) {
                    result[count] = s.substring(start, end);
                    ++count;
                }

                result[count] = s.substring(start, stringLength);
                return result;
            } else {
                return new String[]{s};
            }
        }
    }

    /**
     * 驼峰命名法工具
     *
     * @return toCamelCase(" hello_world ") == "helloWorld"
     * toCapitalizeCamelCase("hello_world") == "HelloWorld"
     * toUnderScoreCase("helloWorld") = "hello_world"
     */
    public static String toCamelCase(String s) {
        if (s == null) {
            return null;
        }

        s = s.toLowerCase();

        StringBuilder sb = new StringBuilder(s.length());
        boolean upperCase = false;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);

            if (c == SEPARATOR) {
                upperCase = true;
            } else if (upperCase) {
                sb.append(Character.toUpperCase(c));
                upperCase = false;
            } else {
                sb.append(c);
            }
        }

        return sb.toString();
    }

    /**
     * 驼峰命名法工具
     *
     * @return toCamelCase(" hello_world ") == "helloWorld"
     * toCapitalizeCamelCase("hello_world") == "HelloWorld"
     * toUnderScoreCase("helloWorld") = "hello_world"
     */
    public static String toCapitalizeCamelCase(String s) {
        if (s == null) {
            return null;
        }
        s = toCamelCase(s);
        return s.substring(0, 1).toUpperCase() + s.substring(1);
    }

    /**
     * 驼峰命名法工具
     *
     * @return toCamelCase(" hello_world ") == "helloWorld"
     * toCapitalizeCamelCase("hello_world") == "HelloWorld"
     * toUnderScoreCase("helloWorld") = "hello_world"
     */
    static String toUnderScoreCase(String s) {
        if (s == null) {
            return null;
        }

        StringBuilder sb = new StringBuilder();
        boolean upperCase = false;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);

            boolean nextUpperCase = true;

            if (i < (s.length() - 1)) {
                nextUpperCase = Character.isUpperCase(s.charAt(i + 1));
            }

            if ((i > 0) && Character.isUpperCase(c)) {
                if (!upperCase || !nextUpperCase) {
                    sb.append(SEPARATOR);
                }
                upperCase = true;
            } else {
                upperCase = false;
            }

            sb.append(Character.toLowerCase(c));
        }

        return sb.toString();
    }

//    /**
//     * 根据ip获取详细地址
//     */
//    public static String getCityInfo(String ip) {
//        DbSearcher searcher = null;
//        try {
//            String path = "ip2region/ip2region.db";
//            String name = "ip2region.db";
//            DbConfig config = new DbConfig();
//            File file = FileUtil.inputStreamToFile(new ClassPathResource(path).getStream(), name);
//            searcher = new DbSearcher(config, file.getPath());
//            Method method;
//            method = searcher.getClass().getMethod("btreeSearch", String.class);
//            DataBlock dataBlock;
//            dataBlock = (DataBlock) method.invoke(searcher, ip);
//            String address = dataBlock.getRegion().replace("0|", "");
//            char symbol = '|';
//            if (address.charAt(address.length() - 1) == symbol) {
//                address = address.substring(0, address.length() - 1);
//            }
//            return address.equals(DataConstant.REGION) ? "内网IP" : address;
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            if (searcher != null) {
//                try {
//                    searcher.close();
//                } catch (IOException ignored) {
//                }
//            }
//
//        }
//        return "";
//    }

    /**
     * 获得当天是周几
     */
    public static String getWeekDay() {
        String[] weekDays = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());

        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0) {
            w = 0;
        }
        return weekDays[w];
    }

    /**
     * 将参数集合map转换成name1=value1&name2=value2 的形式
     *
     * @param map    待转换参数集map
     * @param encode 编码
     * @return String
     */
    public static String mapToRequestParameter(Map map, String encode) {
        StringBuffer sb = new StringBuffer();
        Set set = map.keySet();
        for (Iterator iter = set.iterator(); iter.hasNext(); ) {
            Object name = iter.next();
            if (name != null) {
                String key = (String) name;
                if (key.trim() != "") {
                    Object value = map.get(key);
                    if (value == null) {
                        value = "";
                    }
                    sb.append(key);
                    sb.append("=");
                    try {
                        if (null == encode || "null".equals(encode)) {
                            sb.append(URLEncoder.encode(value.toString()));
                        } else {
                            sb.append(URLEncoder.encode(value.toString(), encode));
                        }

                    } catch (UnsupportedEncodingException e) {
                        RuntimeException exc = new RuntimeException(e.getMessage());
                        exc.setStackTrace(e.getStackTrace());
                        throw exc;
                    }
                    sb.append("&");
                }
            }
        }
        return sb.toString();
    }

    /**
     * @param operationA String type number will do
     * @param operationB String type number will be done
     * @param operator   +|-|*|/
     * @return A operate B
     */
    public static String StringOperation(String operationA, String operationB, String operator) {
        double retDouble = 0;
        String retString = null;
        double changeA = Double.parseDouble(operationA);
        double changeB = Double.parseDouble(operationB);
        if ("+".equals(operator)) {
            retDouble = changeA + changeB;
        }
        if ("-".equals(operator)) {
            retDouble = changeA - changeB;
        }
        if ("*".equals(operator)) {
            retDouble = changeA * changeB;
        }
        if ("/".equals(operator)) {
            retDouble = changeA / changeB;
        }
        retString = String.valueOf(retDouble);
        return retString;
    }

    public static String getUTF8StringFromGBKString(String gbkStr) {
        try {
            return new String(getUTF8BytesFromGBKString(gbkStr), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new InternalError();
        }
    }

    public static byte[] getUTF8BytesFromGBKString(String gbkStr) {
        int n = gbkStr.length();
        byte[] utfBytes = new byte[3 * n];
        int k = 0;
        for (int i = 0; i < n; i++) {
            int m = gbkStr.charAt(i);
            if (m < 128 && m >= 0) {
                utfBytes[k++] = (byte) m;
                continue;
            }
            utfBytes[k++] = (byte) (0xe0 | (m >> 12));
            utfBytes[k++] = (byte) (0x80 | ((m >> 6) & 0x3f));
            utfBytes[k++] = (byte) (0x80 | (m & 0x3f));
        }
        if (k < utfBytes.length) {
            byte[] tmp = new byte[k];
            System.arraycopy(utfBytes, 0, tmp, 0, k);
            return tmp;
        }
        return utfBytes;
    }

    public static void main(String args[]) {

        String s = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                "<ceb:CEB411Message guid=\"202006231829373210000000000000000002\" version=\"1.0\" xmlns:ceb=\"http://www.chinaport.gov.cn/ceb\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">\n" +
                "    <ceb:Payment>\n" +
                "        <ceb:PaymentHead>\n" +
                "            <ceb:guid>202006231829373210000000000000000002</ceb:guid>\n" +
                "            <ceb:appType>1</ceb:appType>\n" +
                "            <ceb:appTime>20200623182938</ceb:appTime>\n" +
                "            <ceb:appStatus>2</ceb:appStatus>\n" +
                "            <ceb:payCode>110896T004</ceb:payCode>\n" +
                "            <ceb:payName>网银在线(北京)科技有限公司</ceb:payName>\n" +
                "            <ceb:payTransactionId>1550648931496</ceb:payTransactionId>\n" +
                "            <ceb:orderNo>117588578</ceb:orderNo>\n" +
                "            <ceb:ebpCode>3302461805</ceb:ebpCode>\n" +
                "            <ceb:ebpName>海囤全球</ceb:ebpName>\n" +
                "            <ceb:payerIdType>1</ceb:payerIdType>\n" +
                "            <ceb:payerIdNumber>445***********3731</ceb:payerIdNumber>\n" +
                "            <ceb:payerName>时凡</ceb:payerName>\n" +
                "            <ceb:telephone>13118227235</ceb:telephone>\n" +
                "            <ceb:amountPaid>0.1</ceb:amountPaid>\n" +
                "            <ceb:currency>142</ceb:currency>\n" +
                "            <ceb:payTime>20190326220423</ceb:payTime>\n" +
                "            <ceb:note></ceb:note>\n" +
                "        </ceb:PaymentHead>\n" +
                "    </ceb:Payment>\n" +
                "    <ceb:BaseTransfer>\n" +
                "        <ceb:copCode>110896T004</ceb:copCode>\n" +
                "        <ceb:copName>网银在线(北京)科技有限公司</ceb:copName>\n" +
                "        <ceb:dxpMode>DXP</ceb:dxpMode>\n" +
                "        <ceb:dxpId>DXPENT0000001417</ceb:dxpId>\n" +
                "        <ceb:note></ceb:note>\n" +
                "    </ceb:BaseTransfer>";
        System.out.println(maskString(s));
    }

    /**
     * 脱敏
     */
    public static final String maskTjString(String s) {
        if (isEmpty(s)) {
            return s;
        }
        String mskStr = s;
        Pattern patternId = Pattern.compile("<payerIdNumber>(\\w+)</payerIdNumber>");
        Matcher matcherId = patternId.matcher(s);
        if (matcherId.find()) {
            String idStr = matcherId.group(1);
            idStr = maskIDCard(idStr);
            mskStr = mskStr.replaceAll("<payerIdNumber>(\\w+)</payerIdNumber>", "<payerIdNumber>" + idStr + "</payerIdNumber>");
        }
        Pattern patternName = Pattern.compile("<payerName>(.*)</payerName>");
        Matcher matcherName = patternName.matcher(s);
        if (matcherName.find()) {
            String nameStr = matcherName.group(1);
            nameStr = maskName(nameStr);
            mskStr = mskStr.replaceAll("<payerName>(.*)</payerName>", "<payerName>" + nameStr + "</payerName>");
        }
        Pattern patternPhone = Pattern.compile("<telephone>(\\d+)</telephone>");
        Matcher matcherPhone = patternPhone.matcher(s);
        if (matcherPhone.find()) {
            String phoneStr = matcherPhone.group(1);
            phoneStr = maskMobile(phoneStr);
            mskStr = mskStr.replaceAll("<telephone>(.*)</telephone>", "<telephone>" + phoneStr + "</telephone>");
        }
        return mskStr;
    }


    /**
     * 脱敏
     */
    public static final String maskString(String s) {
        if (isEmpty(s)) {
            return s;
        }
        String mskStr = s;
        Pattern patternId = Pattern.compile("<ceb:payerIdNumber>(\\w+)</ceb:payerIdNumber>");
        Matcher matcherId = patternId.matcher(s);
        if (matcherId.find()) {
            String idStr = matcherId.group(1);
            idStr = maskIDCard(idStr);
            mskStr = mskStr.replaceAll("<ceb:payerIdNumber>(\\w+)</ceb:payerIdNumber>", "<ceb:payerIdNumber>" + idStr + "</ceb:payerIdNumber>");
        }
        Pattern patternName = Pattern.compile("<ceb:payerName>(.*)</ceb:payerName>");
        Matcher matcherName = patternName.matcher(s);
        if (matcherName.find()) {
            String nameStr = matcherName.group(1);
            nameStr = maskName(nameStr);
            mskStr = mskStr.replaceAll("<ceb:payerName>(.*)</ceb:payerName>", "<ceb:payerName>" + nameStr + "</ceb:payerName>");
        }
        Pattern patternPhone = Pattern.compile("<ceb:telephone>(\\d+)</ceb:telephone>");
        Matcher matcherPhone = patternPhone.matcher(s);
        if (matcherPhone.find()) {
            String phoneStr = matcherPhone.group(1);
            phoneStr = maskMobile(phoneStr);
            mskStr = mskStr.replaceAll("<ceb:telephone>(.*)</ceb:telephone>", "<ceb:telephone>" + phoneStr + "</ceb:telephone>");
        }
        return mskStr;
    }


    /**
     * 手机号显示首3末4位，中间用*号隐藏代替，如：188****5593
     */
    public static String maskMobile(String mobile) {
        if (isEmpty(mobile) || mobile.length() <= 8) {
            return mobile;
        }

        return wordMask(mobile, 3, 4, "*");
    }

    /**
     * 身份证号显示首6末4位，中间用4个*号隐藏代替，如：340121****3754
     */
    public static String maskIDCard(String idCard) {
        if (isEmpty(idCard)) {
            return idCard;
        }

        return wordMask(idCard, 3, 4, "*");
    }

    /**
     * 对字符串进行脱敏处理 --
     *
     * @param word        被脱敏的字符
     * @param startLength 被保留的开始长度 前余n位
     * @param endLength   被保留的结束长度 后余n位
     * @param pad         填充字符
     */
    public static String wordMask(String word, int startLength, int endLength, String pad) {

        if (startLength + endLength > word.length()) {
            return StringUtils.leftPad("", word.length() - 1, pad);
        }

        String startStr = word.substring(0, startLength);

        String endStr = word.substring(word.length() - endLength, word.length());

        return startStr + StringUtils.leftPad("", word.length() - startLength - endLength, pad) + endStr;

    }

    /**
     * 汉字掩码
     * <p>
     * 0-1字，如：用（用）
     * 2字，如：用于（*于）
     * 3-4字，如：用于掩（用*掩）、用于掩码（用**码）
     * 5-6字，如：用于掩码测（用于*码测）、用于掩码测试（用于**测试）
     * 大于6字，如：用于掩码测试的字符串（用于掩****字符串）
     *
     * @param name 姓名
     * @return 掩码后的姓名
     */
    public static String maskName(String name) {
        int lenth = name.length();
        switch (lenth) {
            case 0:
            case 1:
                return name;
            case 2:
                return "*" + name.substring(1, 2);
            case 3:
            case 4:
                return wordMask(name, 1, 1, "*");
            case 5:
            case 6:
                return wordMask(name, 2, 2, "*");
            default:
                return wordMask(name, 3, 3, "*");
        }
    }

}
