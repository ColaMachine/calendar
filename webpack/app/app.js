/*
const PATH = require("path");
console.log("path:"+PATH)
cats = require('./cats.js');
console.log("define cats");*/
//require("!style-loader!css-loader!./css/style.css");
//require("!style-loader!css-loader!./css/sass.scss");
import "./css/common.scss"
import "./css/btn.scss"
import "./css/menu.scss"
import "./css/dropmenu.scss"
import "./css/layout.scss"
import "./css/form.scss"
//import app from "./vueTest"
//const app= require("./head/head.vue");

//document.write("It works1.");
//document.write(require("./content.js"));

import Vue from 'vue'
import head from './module/head/head.vue'
new Vue({
  el: '#head',
  render: h => h(head)
})

//
import VueRouter from 'vue-router'

Vue.use(VueRouter)


/* 实例化一个vue */

import menu from './component/menu/menu.js'

//var menu = new zMenu();

//var menu = require('./component/menu/menu.js');


//menu.init("menu",menuList,{id:"id",url:"url",pid:"pid",name:"name"});


require('./module/route/route.vue');

