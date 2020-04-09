# Angular 常用资料
<font size=4 face="黑体" color=green>【Angular开源库收集资料：】</font>

* element-angular [https://element-angular.faas.ele.me/guide/install](https://element-angular.faas.ele.me/guide/install)

* Covalent UI Platform [https://teradata.github.io/covalent/](https://teradata.github.io/covalent/)

* ngx-charts [https://swimlane.github.io/ngx-charts](https://swimlane.github.io/ngx-charts/) (这个angular2图表引擎不错哟)

* ng2-charts [基于Chart.js的Angular2的美丽图表](http://valor-software.com/ng2-charts/)

* angular2-froala-wysiwyg [AngularJS2版本的Froala WYSIWYG HTML富文本编辑器。](https://github.com/froala/angular2-froala-wysiwyg)

* Kendo UI for Angular 2 [http://www.telerik.com/kendo-angular-ui](http://www.telerik.com/kendo-angular-ui/)

* Fuel-UI [Fuel-UI是基于angular2开发的ui库，包含了几十种ui组件，可以帮你完成大多数开发工作。](http://fuelinteractive.github.io/fuel-ui/)

* Vaadin [https://vaadin.com/](https://github.com/vaadin/expense-manager-ng2-demo)

* ng-lightning [http://ng-lightning.github.io/ng-lightning/#/](https://github.com/ng-lightning/ng-lightning)

* NgxAni [NgxAni 是一个基于 AngularJS2+ 的动画插件](https://a-jie.github.io/NgxAni/)

* ngx-bootstrap [http://valor-software.com/ngx-bootstrap/#/](https://github.com/valor-software/ngx-bootstrap)

* ng-bootstrap [https://ng-bootstrap.github.io/#/](https://github.com/ng-bootstrap/ng-bootstrap)

**ngx-bootstrap VS ng-bootstrap**
```
ngx-bootstrap
安装： npm install ngx-bootstrap --save

再引入css: <link href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta/css/bootstrap.min.css" rel="stylesheet">
还需要再引入 theme: <link href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">

使用方法
一、module文件中引入模块

import { PaginationModule } from 'ngx-bootstrap/pagination';
@NgModule({
  imports: [PaginationModule.forRoot(),...]
})
export class AppModule(){}

二、在component中使用
<pagination [boundaryLinks]="true" [totalItems]="infoList.total_num" (pageChange)="pageChange($event)" [(ngModel)]="pageNo" class="pagination-sm"
            previousText="上一页" nextText="下一页" firstText="第一页" lastText="最后一页">
</pagination>

-------------

ng-bootstrap
安装：npm install --save @ng-bootstrap/ng-bootstrap
再引入css: <link href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta/css/bootstrap.min.css" rel="stylesheet">

使用方法:

一、module文件中引入模块
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';
@NgModule({
  declarations: [AppComponent, ...],
  imports: [NgbModule.forRoot(), ...],
  bootstrap: [AppComponent]
})
二、在component中使用
<ngb-pagination class="d-inline-block" (pageChange)="pageChange($event)" [collectionSize]="infoList.total_num"
                  [(page)]="pageNo" aria-label="Default pagination" [maxSize]="5"
                  [boundaryLinks]="true">
      </ngb-pagination>
```

* PrimeNG [https://www.primefaces.org/primeng/](https://www.primefaces.org/primeng/)

* Angular2 Bootstrap [http://valor-software.com/ngx-bootstrap/](http://valor-software.com/ngx-bootstrap/)

* material2 [angular官网](https://material.angular.io/)

* FreeNG [FreeNG | 基于Angular4的前端UI框架](https://github.com/IronPans/freeng)

* angular-baidu-maps [angular-baidu-maps](https://www.npmjs.com/package/angular-baidu-maps)

* 来自ZTE中兴通讯的开源组件库Jigsaw（七巧板）[https://github.com/rdkmaster/jigsaw](https://github.com/rdkmaster/jigsaw) 或 [http://rdk.zte.com.cn/jigsaw](http://rdk.zte.com.cn/jigsaw)

* ng-bootsrap:[https://github.com/ng-bootstrap/ng-bootstrap](https://github.com/ng-bootstrap/ng-bootstrap) 或 [https://ng-bootstrap.github.io](https://ng-bootstrap.github.io)

* Angular Script :[http://angularscript.com/](http://angularscript.com/)

* ng2-datepicker:[https://github.com/zhenbling/ng2-datepicker](https://github.com/zhenbling/ng2-datepicker)

* 阿里的Angular组件库Zorro [https://github.com/NG-ZORRO/ng-zorro-antd](https://github.com/NG-ZORRO/ng-zorro-antd)
  [https://ant.design/docs/resource/download-cn]（https://ant.design/docs/resource/download-cn）
 [https://ng.ant.design/#/docs/angular/introduce](https://ng.ant.design/#/docs/angular/introduce)

* FreeNG [http://ghmagical.com/#/main/introduction](http://ghmagical.com/#/main/introduction)

* Ngx-Restangular 是为任何从RESTful API获取数据的Web应用程序量身打造的Angular解决方案。 `npm install ngx-restangular`

1、 `npm install mydatepicker ` 日期插件	
   		
2、 `npm install angular2-image-upload --save` 上传插件	
	   	
3、 `npm i angular-froala-wysiwyg`			

4、 `npm install ngx-umeditor --save` Angular2.x for 百度 UMeditor（UEditor）			
https://material.angular.io/

