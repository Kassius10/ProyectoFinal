export function getDateNow() {
    const date = new Date();
    date.setSeconds(0);
    date.setMilliseconds(0);
    return date;
}
export function getDateToday() {
    const date = getDateNow();
    date.setMinutes(0);
    date.setHours(0);
    return date;
}
export function clearTime(date) {
    if (!date) {
        return date;
    }
    date.setHours(0);
    date.setMinutes(0);
    date.setSeconds(0);
    date.setMilliseconds(0);
    return date;
}
export function dateComparer(x, y) {
    if (x === undefined && y === undefined) {
        return true;
    }
    if (x === null && y === null) {
        return true;
    }
    return x <= y && x >= y;
}
//# sourceMappingURL=date-utils.js.map