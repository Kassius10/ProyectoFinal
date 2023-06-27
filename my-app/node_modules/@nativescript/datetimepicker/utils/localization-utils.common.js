export class LocalizationUtilsBase {
    static createNativeLocale(localeIdentifier) {
        return null;
    }
    static createDefaultTimeFormat(context) {
        return "";
    }
    static createNativeDateFormatter(formatPattern, nativeLocale) {
        return null;
    }
    static createNativeTimeFormatter(formatPattern, nativeLocale) {
        return null;
    }
    static formatDateTime(formatter, dateTime) {
        return "";
    }
    static is24Hours(formatter, context) {
        return false;
    }
}
LocalizationUtilsBase.TIME_24H_FORMAT = "HH:mm";
LocalizationUtilsBase.TIME_12H_FORMAT = "h:mm a";
//# sourceMappingURL=localization-utils.common.js.map