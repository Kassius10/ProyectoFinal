import { DatePickerFieldBase } from "./date-picker-field.common";
export class DatePickerField extends DatePickerFieldBase {
    initNativeView() {
        super.initNativeView();
        this.nativeView.setFocusable(false);
    }
}
//# sourceMappingURL=date-picker-field.android.js.map