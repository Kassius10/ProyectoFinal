import { TextField, Property, GestureTypes } from '@nativescript/core';
export class PickerFieldBase extends TextField {
    constructor() {
        super();
        this.editable = false;
    }
    initNativeView() {
        super.initNativeView();
        this._updatePickerFieldBaseTapHandler(true);
    }
    disposeNativeView() {
        this._updatePickerFieldBaseTapHandler(false);
        super.disposeNativeView();
    }
    static localePropertyChanged(field, oldValue, newValue) {
        field.onLocaleChanged(oldValue, newValue);
    }
    onLocaleChanged(oldValue, newValue) { }
    _updatePickerFieldBaseTapHandler(subscribe) {
        if (subscribe) {
            this._pickerFieldBaseTapHandler =
                this._pickerFieldBaseTapHandler ||
                    ((args) => {
                        this._onPickerFieldBaseTap(args);
                    });
            this.on(GestureTypes.tap, this._pickerFieldBaseTapHandler);
        }
        else {
            this.off(GestureTypes.tap, this._pickerFieldBaseTapHandler);
        }
    }
    _onPickerFieldBaseTap(args) {
        if (this.isEnabled) {
            this.open();
        }
    }
}
PickerFieldBase.localeProperty = new Property({
    name: 'locale',
    valueChanged: PickerFieldBase.localePropertyChanged,
});
PickerFieldBase.pickerTitleProperty = new Property({
    name: 'pickerTitle',
});
PickerFieldBase.pickerOkTextProperty = new Property({
    name: 'pickerOkText',
});
PickerFieldBase.pickerCancelTextProperty = new Property({
    name: 'pickerCancelText',
});
PickerFieldBase.localeProperty.register(PickerFieldBase);
PickerFieldBase.pickerTitleProperty.register(PickerFieldBase);
PickerFieldBase.pickerOkTextProperty.register(PickerFieldBase);
PickerFieldBase.pickerCancelTextProperty.register(PickerFieldBase);
//# sourceMappingURL=picker-field-base.js.map