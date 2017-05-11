<template>

  <div id='SysUserList' class='table-responsive'>
    <div class='main-bd '>
      <div class='body_top '>
        <form class='form-inline app-search'>
          <li :key="col.name" v-for='col in config.cols'>
            <label :for='col.name'>{{ col.remarks }}</label>
            <input type='text' :id='col.name' :name='col.name' class='form-control input-sm' maxlength='20' :placeholder="col.remarks"></input>
          </li>

          <label for='username'>用户名</label>
          <input type='text' id='usernameLike' name='usernameLike' class='form-control input-sm' maxlength='20' placeholder='用户名'></input>
          <label for='nkname'>昵称</label>
          <input type='text' id='nknameLike' name='nknameLike' class='form-control input-sm' maxlength='20' placeholder='昵称'></input>

          <label for='status'>状态</label>
          <select id='status' name='status' class='form-control input-sm'>
            <option value=''>-请选择-</option>
            <option value=1>正常</option>
            <option value=2>禁用</option>
            <option value=3>未激活</option>
          </select>
          <label for='email'>邮箱地址</label>
          <input type='text' id='emailLike' name='emailLike' class='form-control input-sm' maxlength='50' placeholder='邮箱地址'></input>
          <label for='telno'>手机号码</label>
          <input type='text' id='telnoLike' name='telnoLike' class='form-control input-sm' maxlength='11' placeholder='手机号码'></input>

          <button type='button' class='btn btn-default searchBtn'>查询</button>
          <button class='btn btn-primary addBtn'><i class='fa fa-plus'></i>新增</button>
        </form>

        <!--<button class='btn btn-primary deleteBtn'><i class='fa fa-plus'></i>删除</button>-->
        <!--<button class='btn btn-primary exportBtn'><i class='fa fa-plus'></i>导出</button>-->
      </div>
      <table id='SysUserGrid' class='grid table'></table>
      <div id='SysUserGrid-Pager' class='pager'></div>
    </div>
  </div>
</template>

<script>
  var SysUserCfg = {
    pkg: 'com.dozenx.web.core.auth',
    name: 'SysUser',
    tableName: 'sys_user',
    remark: '用户',

    cols: [
      {
        name: 'id',
        pk: true,
        ai: true,
        type: 'bigint(15)',
        remark: '编号'
      }, {
        name: 'username',
        remark: '用户名',
        type: 'VARCHAR(20)',
        edit: true,
        nn: true
      },
      {
        name: 'password',
        remark: '密码',
        type: 'VARCHAR(50)',
        edit: true,
        list: false,
        nn: true
      },
      {
        name: 'nkname',
        remark: '昵称',
        type: 'VARCHAR(20)',
        edit: true,
        list: true,
        nn: false
      },
      {
        name: 'type',
        remark: '类型',
        type: 'int(4)',
        edit: false,
        nn: false,
        def: 0,
        list: true
      }, {
        name: 'status',
        remark: '状态',
        edit: true,
        showValue: {
          1: '正常',
          2: '禁用',
          3: '未激活'
        },
        type: 'int(1)',
        def: 0,
        nn: true
      },
      {
        name: 'email',
        remark: '邮箱地址',
        edit: false,
        type: 'varchar(50)',
        valid: 'email',
        nn: false
      }, {
        name: 'telno',
        remark: '手机号码',
        type: 'varchar(11)',
        edit: true,
        valid: 'phone',
        nn: false,
        uq: true
      }, {
        name: 'idcard',
        remark: '身份证号码',
        type: 'varchar(18)',
        edit: true,
        valid: 'id',
        nn: false
      }, {
        name: 'sex',
        remark: '性别',
        type: 'int(1)',
        edit: true,
        showValue: {
          0: '未填写',
          1: '男性',
          2: '女性'
        },
        def: 0,
        list: false,
        nn: false
      }, {
        name: 'birth',
        remark: '出生年月',
        type: 'date',
        edit: true,
        list: false,
        nn: false
      }, {
        name: 'integral',
        remark: '积分',
        type: 'int(11)',
        edit: true,
        list: false,
        nn: false
      }, {
        name: 'address',
        remark: '地址',
        type: 'varchar(50)',
        edit: true,
        list: false,
        nn: false
      }, {
        name: 'weichat',
        remark: '微信',
        type: 'varchar(20)',
        edit: true,
        list: false,
        nn: false
      }, {
        name: 'qq',
        remark: 'qq',
        type: 'bigint(11)',
        edit: true,
        list: false,
        nn: false
      }, {
        name: 'face',
        remark: '头像',
        type: 'varchar(100)',
        edit: true,
        file: 'img',
        def: 'static/img/timg.jpeg',
        nn: false
      }, {
        name: 'remark',
        remark: '备注',
        type: 'varchar(200)',
        edit: true,
        list: false,
        nn: false
      }, {
        name: 'createtime',
        remark: '创建时间',
        type: 'timestamp',
        nn: false,
        def: 'CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP',
        list: false,
        edit: false
      }, {
        name: 'updatetime',
        remark: '更新时间',
        type: 'timestamp',
        list: false,
        edit: false,
        nn: false
      }
    ]
  }
  export default {
    name: 'ListPage',
    data  () {
      return {
        msg: 'Welcome to Your Vue.js App',
        config: SysUserCfg
      }
    }
  }
</script>

<!-- Add 'scoped' attribute to limit CSS to this component only -->
<style scoped>
  h1,
  h2 {
    font-weight: normal;
  }

  ul {
    list-style-type: none;
    padding: 0;
  }

  li {
    display: inline-block;
    margin: 0 10px;
  }

  a {
    color: #42b983;
  }
</style>
