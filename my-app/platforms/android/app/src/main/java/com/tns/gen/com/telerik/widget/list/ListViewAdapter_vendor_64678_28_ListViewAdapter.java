/* AUTO-GENERATED FILE. DO NOT MODIFY.
 * This class was automatically generated by the
 * static binding generator from the resources it found.
 * Please do not modify by hand.
 */
package com.tns.gen.com.telerik.widget.list;

public class ListViewAdapter_vendor_64678_28_ListViewAdapter
    extends com.telerik.widget.list.ListViewAdapter
    implements com.tns.NativeScriptHashCodeProvider {
  public ListViewAdapter_vendor_64678_28_ListViewAdapter(java.util.List param_0) {
    super(param_0);
    com.tns.Runtime.initInstance(this);
  }

  public ListViewAdapter_vendor_64678_28_ListViewAdapter(
      java.util.List param_0, com.telerik.android.data.SelectionAdapter param_1) {
    super(param_0, param_1);
    com.tns.Runtime.initInstance(this);
  }

  public boolean reorderItem(int param_0, int param_1) {
    java.lang.Object[] args = new java.lang.Object[2];
    args[0] = param_0;
    args[1] = param_1;
    return (boolean) com.tns.Runtime.callJSMethod(this, "reorderItem", boolean.class, args);
  }

  public void setItems(java.util.List param_0) {
    java.lang.Object[] args = new java.lang.Object[1];
    args[0] = param_0;
    com.tns.Runtime.callJSMethod(this, "setItems", void.class, args);
  }

  public com.telerik.widget.list.ListViewHolder onCreateViewHolder(
      android.view.ViewGroup param_0, int param_1) {
    java.lang.Object[] args = new java.lang.Object[2];
    args[0] = param_0;
    args[1] = param_1;
    return (com.telerik.widget.list.ListViewHolder)
        com.tns.Runtime.callJSMethod(
            this, "onCreateViewHolder", com.telerik.widget.list.ListViewHolder.class, args);
  }

  public void onBindViewHolder(com.telerik.widget.list.ListViewHolder param_0, int param_1) {
    java.lang.Object[] args = new java.lang.Object[2];
    args[0] = param_0;
    args[1] = param_1;
    com.tns.Runtime.callJSMethod(this, "onBindViewHolder", void.class, args);
  }

  public com.telerik.widget.list.ListViewHolder onCreateSwipeContentHolder(
      android.view.ViewGroup param_0) {
    java.lang.Object[] args = new java.lang.Object[1];
    args[0] = param_0;
    return (com.telerik.widget.list.ListViewHolder)
        com.tns.Runtime.callJSMethod(
            this, "onCreateSwipeContentHolder", com.telerik.widget.list.ListViewHolder.class, args);
  }

  public void onBindSwipeContentHolder(
      com.telerik.widget.list.ListViewHolder param_0, int param_1) {
    java.lang.Object[] args = new java.lang.Object[2];
    args[0] = param_0;
    args[1] = param_1;
    com.tns.Runtime.callJSMethod(this, "onBindSwipeContentHolder", void.class, args);
  }

  public boolean canSelect(int param_0) {
    java.lang.Object[] args = new java.lang.Object[1];
    args[0] = param_0;
    return (boolean) com.tns.Runtime.callJSMethod(this, "canSelect", boolean.class, args);
  }

  public boolean canSwipe(int param_0) {
    java.lang.Object[] args = new java.lang.Object[1];
    args[0] = param_0;
    return (boolean) com.tns.Runtime.callJSMethod(this, "canSwipe", boolean.class, args);
  }

  public boolean canReorder(int param_0) {
    java.lang.Object[] args = new java.lang.Object[1];
    args[0] = param_0;
    return (boolean) com.tns.Runtime.callJSMethod(this, "canReorder", boolean.class, args);
  }

  public void onBindViewHolder(
      com.telerik.widget.list.ListViewHolder param_0,
      int param_1,
      java.util.List<java.lang.Object> param_2) {
    java.lang.Object[] args = new java.lang.Object[3];
    args[0] = param_0;
    args[1] = param_1;
    args[2] = param_2;
    com.tns.Runtime.callJSMethod(this, "onBindViewHolder", void.class, args);
  }

  public int getItemViewType(int param_0) {
    java.lang.Object[] args = new java.lang.Object[1];
    args[0] = param_0;
    return (int) com.tns.Runtime.callJSMethod(this, "getItemViewType", int.class, args);
  }

  public int hashCode__super() {
    return super.hashCode();
  }

  public boolean equals__super(java.lang.Object other) {
    return super.equals(other);
  }
}
