/* AUTO-GENERATED FILE. DO NOT MODIFY.
 * This class was automatically generated by the
 * static binding generator from the resources it found.
 * Please do not modify by hand.
 */
package com.tns.gen.java.lang;

public class Object_vendor_23321_28_EditTextListenersImpl extends java.lang.Object
    implements com.tns.NativeScriptHashCodeProvider,
        android.text.TextWatcher,
        android.view.View.OnFocusChangeListener,
        android.widget.TextView.OnEditorActionListener {
  public Object_vendor_23321_28_EditTextListenersImpl() {
    super();
    com.tns.Runtime.initInstance(this);
  }

  public void beforeTextChanged(
      java.lang.CharSequence param_0, int param_1, int param_2, int param_3) {
    java.lang.Object[] args = new java.lang.Object[4];
    args[0] = param_0;
    args[1] = param_1;
    args[2] = param_2;
    args[3] = param_3;
    com.tns.Runtime.callJSMethod(this, "beforeTextChanged", void.class, args);
  }

  public void onTextChanged(java.lang.CharSequence param_0, int param_1, int param_2, int param_3) {
    java.lang.Object[] args = new java.lang.Object[4];
    args[0] = param_0;
    args[1] = param_1;
    args[2] = param_2;
    args[3] = param_3;
    com.tns.Runtime.callJSMethod(this, "onTextChanged", void.class, args);
  }

  public void afterTextChanged(android.text.Editable param_0) {
    java.lang.Object[] args = new java.lang.Object[1];
    args[0] = param_0;
    com.tns.Runtime.callJSMethod(this, "afterTextChanged", void.class, args);
  }

  public void onFocusChange(android.view.View param_0, boolean param_1) {
    java.lang.Object[] args = new java.lang.Object[2];
    args[0] = param_0;
    args[1] = param_1;
    com.tns.Runtime.callJSMethod(this, "onFocusChange", void.class, args);
  }

  public boolean onEditorAction(
      android.widget.TextView param_0, int param_1, android.view.KeyEvent param_2) {
    java.lang.Object[] args = new java.lang.Object[3];
    args[0] = param_0;
    args[1] = param_1;
    args[2] = param_2;
    return (boolean) com.tns.Runtime.callJSMethod(this, "onEditorAction", boolean.class, args);
  }

  public int hashCode__super() {
    return super.hashCode();
  }

  public boolean equals__super(java.lang.Object other) {
    return super.equals(other);
  }
}
