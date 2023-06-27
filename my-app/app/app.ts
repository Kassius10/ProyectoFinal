import { Application, Frame } from '@nativescript/core'
import { SecureStorage } from '@nativescript/secure-storage'

Application.run({
   create: () => {
     const frame = new Frame();
     frame.navigate({
       moduleName: initData(),
       backstackVisible: true
     });
     return frame;
   }
 });

function initData(): string{
  const secureStorage = new SecureStorage();
  const token = secureStorage.getSync({key: "token"});

  if(token){
    return "app-root"
  }
  return "login/login-page"
}
