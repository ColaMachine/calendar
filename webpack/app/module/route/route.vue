<template>

</template>


<script>
// 首先定义或者引入路由的组件
// 方法一：直接定义路由组件
/*const goods = { template: '<p>goods</p>' };
const ratings = { template: '<p>ratings</p>' };
const seller = { template: '<p>seller</p>' };*/
// 方法二：import引入路由组件
import VueRouter from 'vue-router'
import Vue from 'vue'
import buttonExample from '../../module/example/general/buttonExample.vue'

import gridExample from '../../module/example/layout/gridExample.vue';
import layoutExample from '../../module/example/layout/layoutExample.vue';
import apiExample from '../../module/example/api/apiExample.vue';
import collapseExample from '../../module/example/datadisplay/collapseExample.vue';
import menuExample from '../../module/example/navigation/menuExample.vue';
import dropdownExample from '../../module/example/navigation/dropdownExample.vue';
import siteExample from '../../module/example/site/siteExample.vue';
import inputExample from '../../module/example/dataentry/inputExample.vue';
// 然后定义路由(routes)，components还可以是Vue.extend()创建的
const routes = [
  { path: '/button', component: buttonExample },
   { path: '/layout', component: layoutExample },
 { path: '/grid', component: gridExample },
 { path: '/api', component: apiExample },
 { path: '/collapse', component:collapseExample },
 { path: '/menu', component:menuExample },
 { path: '/dorpdown', component:dropdownExample },
  { path: '/site', component:siteExample },
    { path: '/input', component:inputExample },
];
// 接着创建路由实例
const router = new VueRouter({
  // ES6缩写语法，相当于routes:routes
  routes
});
// 最后创建vue实例并挂载
const routeApp = new Vue({
  el: '#router',
  router
});
// 或者
/**const app = new Vue({
  router
}).$mount('#app')**/
</script>