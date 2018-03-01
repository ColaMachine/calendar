/**
 * 版权所有: 爱WiFi无线运营中心
 * 创建日期: 2017-02-16 11:02
 * 创建作者: 朱学煌 17705818601@189.cn
 * 文件名称: route
 * 版本: v1.0
 * 功能: xx
 * 修改记录: xx
 */

import querystring from 'querystring';

// 路由信息对象的属性
//
// $route.path
// 类型: string
// 字符串，对应当前路由的路径，总是解析为绝对路径，如 "/foo/bar"。
//
// $route.params
// 类型: Object
// 一个 key/value 对象，包含了 动态片段 和 全匹配片段，如果没有路由参数，就是一个空对象。
//
// $route.query
// 类型: Object
// 一个 key/value 对象，表示 URL 查询参数。例如，对于路径 /foo?user=1，则有 $route.query.user == 1，如果没有查询参数，则是个空对象。
//
// $route.hash
// 类型: string
// 当前路由的 hash 值 (带 #) ，如果没有 hash 值，则为空字符串
//
// $route.fullPath
// 类型: string
// 完成解析后的 URL，包含查询参数和 hash 的完整路径

function hashList() {
    return window.location.hash.split('?');
}

export default {
    // 返回hash.query数据对象 {key: val}
    query: function () {
        const routeHashList = hashList();
        if (routeHashList && routeHashList[1]) {
            return querystring.parse(routeHashList[1]) || {};
        }
        return {};
    },

    // 返回hash.path值（不带#） pubsrv/index
    path: function () {
        const routeHashList = hashList();
        if (routeHashList && routeHashList[0]) {
            return routeHashList[0].substr(1);
        }
        return '';
    },

    // 返回hash.path 数组（不带#）['pubsrv', 'index']
    params: function () {
        const routeHashList = hashList();
        if (routeHashList && routeHashList[0]) {
            return routeHashList[0].substr(1).split('/').splice(1);
        }
        return [];
    },
}