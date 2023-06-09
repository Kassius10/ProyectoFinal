"use strict";
exports.id = "bundle";
exports.ids = null;
exports.modules = {

/***/ "./app/home/home-eventos-page.ts":
/***/ ((module, __webpack_exports__, __webpack_require__) => {

__webpack_require__.r(__webpack_exports__);
/* harmony export */ __webpack_require__.d(__webpack_exports__, {
/* harmony export */   lookRanking: () => (/* binding */ lookRanking),
/* harmony export */   onItemSelected: () => (/* binding */ onItemSelected),
/* harmony export */   onLoaded: () => (/* binding */ onLoaded),
/* harmony export */   onNavigatingTo: () => (/* binding */ onNavigatingTo),
/* harmony export */   tap: () => (/* binding */ tap)
/* harmony export */ });
/* harmony import */ var _nativescript_core__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__("./node_modules/@nativescript/core/index.js");
/* harmony import */ var date_fns__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__("./node_modules/date-fns/esm/format/index.js");
/* harmony import */ var _home_view_model__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__("./app/home/home-view-model.ts");
/* harmony import */ var _nativescript_secure_storage__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__("./node_modules/@nativescript/secure-storage/secure-storage.android.js");




var secure = new _nativescript_secure_storage__WEBPACK_IMPORTED_MODULE_1__.SecureStorage();
function onNavigatingTo(args) {
    const page = args.object;
    const token = secure.getSync({ key: "token" });
    load(page, token);
}
function tap(args) {
    const state = args.state;
    const page = args.object;
    const token = secure.getSync({ key: "token" });
    if (state === 3) {
        load(page, token);
    }
}
function onLoaded(args) {
    const page = args.object;
    const token = secure.getSync({ key: "token" });
    load(page, token);
}
function onItemSelected(args) {
    const view = args.view;
    const page = view.page;
    const tappedItem = view.bindingContext;
    page.frame.navigate({
        moduleName: 'home/home-evento-detail/home-evento-detail-page',
        context: tappedItem,
        animated: true,
        transition: {
            name: 'slide',
            duration: 200,
            curve: 'ease',
        },
    });
}
function lookRanking(args) {
    const button = args.object;
    const page = button.page;
    const evento = button.bindingContext;
    page.frame.navigate({
        moduleName: 'home/evento-ranking/evento-ranking-page',
        context: evento,
        animated: true,
        transition: {
            name: 'slide',
            duration: 200,
            curve: 'ease',
        },
    });
}
function load(page, token) {
    _nativescript_core__WEBPACK_IMPORTED_MODULE_2__.Http.request({
        url: "http:192.168.3.25:8080/evento",
        method: "GET",
        headers: { "Authorization": "Bearer " + token }
    })
        .then((response) => {
        const viewModel = new _home_view_model__WEBPACK_IMPORTED_MODULE_0__.HomeViewModel();
        viewModel.datosApi = [];
        viewModel.datosApi = response.content.toJSON();
        viewModel.datosApi.forEach(dato => {
            dato.fecha = (0,date_fns__WEBPACK_IMPORTED_MODULE_3__["default"])(new Date(dato.fecha), 'dd/MM/yyyy hh:mm');
        });
        page.bindingContext = viewModel;
    }, (e) => {
        console.log(e);
    });
}

/* NATIVESCRIPT-HOT-LOADER */
if( true && global._isModuleLoadedForUI && global._isModuleLoadedForUI("./home/home-eventos-page.ts")) {
	module.hot.accept()
}

/***/ })

};
;
//# sourceMappingURL=data:application/json;charset=utf-8;base64,eyJ2ZXJzaW9uIjozLCJmaWxlIjoiYnVuZGxlLmM5ZmUyOWM3MDdlYTgwOTAwYzRjLmhvdC11cGRhdGUuanMiLCJtYXBwaW5ncyI6Ijs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7QUFDeUM7QUFDUjtBQUVnQjtBQUVXO0FBRzVELElBQUksTUFBTSxHQUFHLElBQUksdUVBQWEsRUFBRTtBQUV6QixTQUFTLGNBQWMsQ0FBQyxJQUFtQjtJQUNoRCxNQUFNLElBQUksR0FBUyxJQUFJLENBQUMsTUFBTTtJQUM5QixNQUFNLEtBQUssR0FBRyxNQUFNLENBQUMsT0FBTyxDQUFDLEVBQUMsR0FBRyxFQUFFLE9BQU8sRUFBQyxDQUFDO0lBQzVDLElBQUksQ0FBQyxJQUFJLEVBQUUsS0FBSyxDQUFDO0FBQ25CLENBQUM7QUFDTSxTQUFTLEdBQUcsQ0FBQyxJQUF5QjtJQUMzQyxNQUFNLEtBQUssR0FBRyxJQUFJLENBQUMsS0FBSztJQUN4QixNQUFNLElBQUksR0FBRyxJQUFJLENBQUMsTUFBYztJQUNoQyxNQUFNLEtBQUssR0FBRyxNQUFNLENBQUMsT0FBTyxDQUFDLEVBQUMsR0FBRyxFQUFFLE9BQU8sRUFBQyxDQUFDO0lBRTVDLElBQUksS0FBSyxLQUFLLENBQUMsRUFBRTtRQUNmLElBQUksQ0FBQyxJQUFJLEVBQUUsS0FBSyxDQUFDO0tBQ2xCO0FBQ0gsQ0FBQztBQUNNLFNBQVMsUUFBUSxDQUFDLElBQWU7SUFDdEMsTUFBTSxJQUFJLEdBQUcsSUFBSSxDQUFDLE1BQWM7SUFDaEMsTUFBTSxLQUFLLEdBQUcsTUFBTSxDQUFDLE9BQU8sQ0FBQyxFQUFDLEdBQUcsRUFBRSxPQUFPLEVBQUMsQ0FBQztJQUU1QyxJQUFJLENBQUMsSUFBSSxFQUFFLEtBQUssQ0FBQztBQUNuQixDQUFDO0FBSU0sU0FBUyxjQUFjLENBQUMsSUFBbUI7SUFDaEQsTUFBTSxJQUFJLEdBQVMsSUFBSSxDQUFDLElBQUk7SUFDNUIsTUFBTSxJQUFJLEdBQVMsSUFBSSxDQUFDLElBQUk7SUFDNUIsTUFBTSxVQUFVLEdBQVcsSUFBSSxDQUFDLGNBQWM7SUFFOUMsSUFBSSxDQUFDLEtBQUssQ0FBQyxRQUFRLENBQUM7UUFDbEIsVUFBVSxFQUFFLGlEQUFpRDtRQUM3RCxPQUFPLEVBQUUsVUFBVTtRQUNuQixRQUFRLEVBQUUsSUFBSTtRQUNkLFVBQVUsRUFBRTtZQUNWLElBQUksRUFBRSxPQUFPO1lBQ2IsUUFBUSxFQUFFLEdBQUc7WUFDYixLQUFLLEVBQUUsTUFBTTtTQUNkO0tBQ0YsQ0FBQztBQUNKLENBQUM7QUFFTSxTQUFTLFdBQVcsQ0FBQyxJQUFlO0lBQ3pDLE1BQU0sTUFBTSxHQUFHLElBQUksQ0FBQyxNQUFnQjtJQUNwQyxNQUFNLElBQUksR0FBRyxNQUFNLENBQUMsSUFBSTtJQUN4QixNQUFNLE1BQU0sR0FBRyxNQUFNLENBQUMsY0FBd0I7SUFFOUMsSUFBSSxDQUFDLEtBQUssQ0FBQyxRQUFRLENBQUM7UUFDbEIsVUFBVSxFQUFFLHlDQUF5QztRQUNyRCxPQUFPLEVBQUUsTUFBTTtRQUNmLFFBQVEsRUFBRSxJQUFJO1FBQ2QsVUFBVSxFQUFFO1lBQ1YsSUFBSSxFQUFFLE9BQU87WUFDYixRQUFRLEVBQUUsR0FBRztZQUNiLEtBQUssRUFBRSxNQUFNO1NBQ2Q7S0FDRixDQUFDO0FBQ0osQ0FBQztBQUVELFNBQVMsSUFBSSxDQUFDLElBQVUsRUFBRSxLQUFhO0lBQ3JDLDREQUFZLENBQUM7UUFDWCxHQUFHLEVBQUUsK0JBQStCO1FBQ3BDLE1BQU0sRUFBRSxLQUFLO1FBQ2IsT0FBTyxFQUFFLEVBQUUsZUFBZSxFQUFFLFNBQVMsR0FBRyxLQUFLLEVBQUU7S0FDaEQsQ0FBQztTQUNELElBQUksQ0FDSCxDQUFDLFFBQVEsRUFBRSxFQUFFO1FBQ2IsTUFBTSxTQUFTLEdBQUcsSUFBSSwyREFBYSxFQUFFO1FBQ3JDLFNBQVMsQ0FBQyxRQUFRLEdBQUcsRUFBRTtRQUN2QixTQUFTLENBQUMsUUFBUSxHQUFHLFFBQVEsQ0FBQyxPQUFPLENBQUMsTUFBTSxFQUFFO1FBQzlDLFNBQVMsQ0FBQyxRQUFRLENBQUMsT0FBTyxDQUFDLElBQUksQ0FBQyxFQUFFO1lBQ2hDLElBQUksQ0FBQyxLQUFLLEdBQUcsb0RBQU0sQ0FBQyxJQUFJLElBQUksQ0FBQyxJQUFJLENBQUMsS0FBSyxDQUFDLEVBQUUsa0JBQWtCLENBQUM7UUFDL0QsQ0FBQyxDQUFDO1FBQ0YsSUFBSSxDQUFDLGNBQWMsR0FBRyxTQUFTO0lBR2pDLENBQUMsRUFBQyxDQUFDLENBQUMsRUFBRSxFQUFFO1FBQ04sT0FBTyxDQUFDLEdBQUcsQ0FBQyxDQUFDLENBQUM7SUFDaEIsQ0FBQyxDQUFDO0FBQ0osQ0FBQyIsInNvdXJjZXMiOlsid2VicGFjazovL215LWFwcC8uL2FwcC9ob21lL2hvbWUtZXZlbnRvcy1wYWdlLnRzIl0sInNvdXJjZXNDb250ZW50IjpbImltcG9ydCB7IFZpZXcsIEl0ZW1FdmVudERhdGEsIE5hdmlnYXRlZERhdGEsIFBhZ2UsIFN0YWNrTGF5b3V0LCBTdHlsZSwgUGFuR2VzdHVyZUV2ZW50RGF0YSwgRXZlbnREYXRhLCBCdXR0b24gfSBmcm9tICdAbmF0aXZlc2NyaXB0L2NvcmUnXG5pbXBvcnQgeyBIdHRwIH0gZnJvbSAnQG5hdGl2ZXNjcmlwdC9jb3JlJ1xuaW1wb3J0IHsgZm9ybWF0IH0gZnJvbSBcImRhdGUtZm5zXCJcblxuaW1wb3J0IHsgSG9tZVZpZXdNb2RlbCB9IGZyb20gJy4vaG9tZS12aWV3LW1vZGVsJ1xuaW1wb3J0IHsgRXZlbnRvIH0gZnJvbSAnLi9zaGFyZWQvZXZlbnRvJ1xuaW1wb3J0IHsgU2VjdXJlU3RvcmFnZSB9IGZyb20gJ0BuYXRpdmVzY3JpcHQvc2VjdXJlLXN0b3JhZ2UnXG5cblxudmFyIHNlY3VyZSA9IG5ldyBTZWN1cmVTdG9yYWdlKClcblxuZXhwb3J0IGZ1bmN0aW9uIG9uTmF2aWdhdGluZ1RvKGFyZ3M6IE5hdmlnYXRlZERhdGEpIHtcbiAgY29uc3QgcGFnZSA9IDxQYWdlPmFyZ3Mub2JqZWN0XG4gIGNvbnN0IHRva2VuID0gc2VjdXJlLmdldFN5bmMoe2tleTogXCJ0b2tlblwifSlcbiAgbG9hZChwYWdlLCB0b2tlbilcbn1cbmV4cG9ydCBmdW5jdGlvbiB0YXAoYXJnczogUGFuR2VzdHVyZUV2ZW50RGF0YSkge1xuICBjb25zdCBzdGF0ZSA9IGFyZ3Muc3RhdGVcbiAgY29uc3QgcGFnZSA9IGFyZ3Mub2JqZWN0IGFzIFBhZ2VcbiAgY29uc3QgdG9rZW4gPSBzZWN1cmUuZ2V0U3luYyh7a2V5OiBcInRva2VuXCJ9KVxuXG4gIGlmIChzdGF0ZSA9PT0gMykge1xuICAgIGxvYWQocGFnZSwgdG9rZW4pXG4gIH1cbn1cbmV4cG9ydCBmdW5jdGlvbiBvbkxvYWRlZChhcmdzOiBFdmVudERhdGEpIHtcbiAgY29uc3QgcGFnZSA9IGFyZ3Mub2JqZWN0IGFzIFBhZ2VcbiAgY29uc3QgdG9rZW4gPSBzZWN1cmUuZ2V0U3luYyh7a2V5OiBcInRva2VuXCJ9KVxuXG4gIGxvYWQocGFnZSwgdG9rZW4pXG59XG5cblxuXG5leHBvcnQgZnVuY3Rpb24gb25JdGVtU2VsZWN0ZWQoYXJnczogSXRlbUV2ZW50RGF0YSkge1xuICBjb25zdCB2aWV3ID0gPFZpZXc+YXJncy52aWV3XG4gIGNvbnN0IHBhZ2UgPSA8UGFnZT52aWV3LnBhZ2VcbiAgY29uc3QgdGFwcGVkSXRlbSA9IDxFdmVudG8+dmlldy5iaW5kaW5nQ29udGV4dFxuXG4gIHBhZ2UuZnJhbWUubmF2aWdhdGUoe1xuICAgIG1vZHVsZU5hbWU6ICdob21lL2hvbWUtZXZlbnRvLWRldGFpbC9ob21lLWV2ZW50by1kZXRhaWwtcGFnZScsXG4gICAgY29udGV4dDogdGFwcGVkSXRlbSxcbiAgICBhbmltYXRlZDogdHJ1ZSxcbiAgICB0cmFuc2l0aW9uOiB7XG4gICAgICBuYW1lOiAnc2xpZGUnLFxuICAgICAgZHVyYXRpb246IDIwMCxcbiAgICAgIGN1cnZlOiAnZWFzZScsXG4gICAgfSxcbiAgfSlcbn1cblxuZXhwb3J0IGZ1bmN0aW9uIGxvb2tSYW5raW5nKGFyZ3M6IEV2ZW50RGF0YSl7XG4gIGNvbnN0IGJ1dHRvbiA9IGFyZ3Mub2JqZWN0IGFzIEJ1dHRvblxuICBjb25zdCBwYWdlID0gYnV0dG9uLnBhZ2VcbiAgY29uc3QgZXZlbnRvID0gYnV0dG9uLmJpbmRpbmdDb250ZXh0IGFzIEV2ZW50b1xuXG4gIHBhZ2UuZnJhbWUubmF2aWdhdGUoe1xuICAgIG1vZHVsZU5hbWU6ICdob21lL2V2ZW50by1yYW5raW5nL2V2ZW50by1yYW5raW5nLXBhZ2UnLFxuICAgIGNvbnRleHQ6IGV2ZW50byxcbiAgICBhbmltYXRlZDogdHJ1ZSxcbiAgICB0cmFuc2l0aW9uOiB7XG4gICAgICBuYW1lOiAnc2xpZGUnLFxuICAgICAgZHVyYXRpb246IDIwMCxcbiAgICAgIGN1cnZlOiAnZWFzZScsXG4gICAgfSxcbiAgfSlcbn1cblxuZnVuY3Rpb24gbG9hZChwYWdlOiBQYWdlLCB0b2tlbjogc3RyaW5nKXtcbiAgSHR0cC5yZXF1ZXN0KHtcbiAgICB1cmw6IFwiaHR0cDoxOTIuMTY4LjMuMjU6ODA4MC9ldmVudG9cIixcbiAgICBtZXRob2Q6IFwiR0VUXCIsXG4gICAgaGVhZGVyczogeyBcIkF1dGhvcml6YXRpb25cIjogXCJCZWFyZXIgXCIgKyB0b2tlbiB9XG4gIH0pXG4gIC50aGVuKFxuICAgIChyZXNwb25zZSkgPT57XG4gICAgY29uc3Qgdmlld01vZGVsID0gbmV3IEhvbWVWaWV3TW9kZWwoKVxuICAgIHZpZXdNb2RlbC5kYXRvc0FwaSA9IFtdXG4gICAgdmlld01vZGVsLmRhdG9zQXBpID0gcmVzcG9uc2UuY29udGVudC50b0pTT04oKVxuICAgIHZpZXdNb2RlbC5kYXRvc0FwaS5mb3JFYWNoKGRhdG8gPT57XG4gICAgICBkYXRvLmZlY2hhID0gZm9ybWF0KG5ldyBEYXRlKGRhdG8uZmVjaGEpLCAnZGQvTU0veXl5eSBoaDptbScpXG4gICAgfSlcbiAgICBwYWdlLmJpbmRpbmdDb250ZXh0ID0gdmlld01vZGVsXG5cbiAgICBcbiAgfSwoZSkgPT57XG4gICAgY29uc29sZS5sb2coZSlcbiAgfSlcbn1cbiJdLCJuYW1lcyI6W10sInNvdXJjZVJvb3QiOiIifQ==