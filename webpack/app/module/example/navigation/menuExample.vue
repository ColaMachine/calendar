<template>
<div>
<article>
<section>
<h2>Menu导航菜单</h2>
<p>为页面和功能提供导航的菜单列表。</p>
<h3>何时使用</h3>
<p>导航菜单是一个网站的灵魂，用户依赖导航在各个页面中进行跳转。一般分为顶部导航和侧边导航，顶部导航提供全局性的类目和功能，侧边导航提供多级结构来收纳和排列网站架构。</p>
<p>更多布局和导航的使用可以参考：通用布局。</p>
<h3>代码演示<zwIcon type="windows" title="展开全部代码"></zwIcon></h3>
<zwRow >
<zwCol span=24>

    <!---------按钮类型--------------->
    <zwBox class="">

     <div slot="zwCodeBoxDemo">
       <zwMenu mode="horizontal">
          <zwMenuItem key="mail">
            <zwIcon type="mail" />Navigation One
          </zwMenuItem>
          <zwMenuItem key="app" disabled>
            <zwIcon type="appstore" />Navigation Two
          </zwMenuItem>



       <zwSubMenu >
        <template slot="title">    <zwIcon type="appstore" />Navigation three</template>

                  <zwMenuItemGroup title="Item 1">
                    <zwMenuItem key="setting:1">Option 1</zwMenuItem>
                    <zwMenuItem key="setting:2">Option 2</zwMenuItem>
                  </zwMenuItemGroup>
                  <zwMenuItemGroup title="Item 2">
                    <zwMenuItem key="setting:3">Option 3</zwMenuItem>
                    <zwMenuItem key="setting:4">Option 4</zwMenuItem>
                  </zwMenuItemGroup>

                </zwSubMenu>


          <zwMenuItem key="alipay">
            <a href="https://ant.design" target="_blank" rel="noopener noreferrer">Navigation Four - Link</a>
          </zwMenuItem>
        </zwMenu>
    </div>
     <div slot="zwCodeBoxMeta"  title="顶部导航">



       水平的顶部导航菜单。
    </div>
                <div slot="zwCodeBoxPanel"  >

                    <textarea ref="textarea" id="textarea" name="textarea">
                        <zwButton type="primary" shape="circle" icon="search"></zwButton>
                        <zwButton type="primary"  icon="search">search</zwButton>

                        <zwButton  shape="circle" icon="search"></zwButton>
                        <zwButton   icon="search">search</zwButton>

                        <br />

                        <zwButton type="dashed" shape="circle" icon="search"></zwButton>
                        <zwButton type="dashed"  icon="search">search</zwButton>

                        <zwButton type="danger" shape="circle" icon="search"></zwButton>
                        <zwButton type="danger"  icon="search">search</zwButton>
                    </textarea>

                </div>

    </zwBox>
<!---------垂直菜单--------------->
    <zwBox class="">
            <div slot="zwCodeBoxDemo" class="">
               <zwMenu mode="inline">
                        <zwMenuItem key="mail">
                          <zwIcon type="mail" />Navigation One
                        </zwMenuItem>
                        <zwMenuItem key="app" disabled>
                          <zwIcon type="appstore" />Navigation Two
                        </zwMenuItem>

  <zwSubMenu  >
                      <template slot="title">    <zwIcon type="search" />has child item</template>


                                  <zwMenuItem key="setting:1">Option 1</zwMenuItem>
                                  <zwMenuItem key="setting:2">Option 2</zwMenuItem>


                              </zwSubMenu>



                     <zwSubMenu >
                        <template slot="title">    <zwIcon type="search" />has child submenu</template>


                       <zwMenuItem key="setting:1">Option 1</zwMenuItem>
                       <zwMenuItem key="setting:2">Option 2</zwMenuItem>
                        <zwSubMenu >
                        <template slot="title">    <zwIcon type="search" />has child item</template>


                        <zwMenuItem key="setting:1">Option 1</zwMenuItem>
                        <zwMenuItem key="setting:2">Option 2</zwMenuItem>


                        </zwSubMenu>

                    </zwSubMenu>

                      <zwSubMenu >

                      <template slot="title">    <zwIcon type="search" />has child itemgroup</template>

                                <zwMenuItemGroup title="Item 1">
                                  <zwMenuItem key="setting:1">Option 1</zwMenuItem>
                                  <zwMenuItem key="setting:2">Option 2</zwMenuItem>
                                </zwMenuItemGroup>
                                <zwMenuItemGroup title="Item 2">
                                  <zwMenuItem key="setting:3">Option 3</zwMenuItem>
                                  <zwMenuItem key="setting:4">Option 4</zwMenuItem>
                                </zwMenuItemGroup>

                              </zwSubMenu>


                        <zwMenuItem key="alipay">
                          <a href="https://ant.design" target="_blank" rel="noopener noreferrer">Navigation Four - Link</a>
                        </zwMenuItem>
                      </zwMenu>
            </div>
             <div slot="zwCodeBoxMeta"  title="按钮尺寸">

                按钮有大、中、小三种尺寸。
                通过设置 size 为 large small 分别把按钮设为大、小尺寸。若不设置 size，则尺寸为中。
            </div>
            <div slot="zwCodeBoxPanel"  >

                <textarea ref="textarea" id="textarea" name="textarea">
                    <zwButton type="primary" shape="circle" icon="search"></zwButton>
                    <zwButton type="primary"  icon="search">search</zwButton>

                    <zwButton  shape="circle" icon="search"></zwButton>
                    <zwButton   icon="search">search</zwButton>

                    <br />

                    <zwButton type="dashed" shape="circle" icon="search"></zwButton>
                    <zwButton type="dashed"  icon="search">search</zwButton>

                    <zwButton type="danger" shape="circle" icon="search"></zwButton>
                    <zwButton type="danger"  icon="search">search</zwButton>
                </textarea>

            </div>
        </zwBox>

</zwCol>



<zwCol span=12>

</zwCol>




</zwRow>

</section>
</article>
</div>
</template>
<script>
import zwButton from '../../../component/button/zwButton.vue';
import zwButtonGroup from '../../../component/button/zwButtonGroup.vue';
import zwIcon from '../../../component/icon/zwIcon.vue';
import zwRow from '../../../component/layout/zwRow.vue';
import zwBox from '../../../component/layout/zwBox.vue';
import zwDropDown from '../../../component/navigation/menu/zwDropDown.vue';
import zwMenu from '../../../component/navigation/menu/zwMenu.vue';
import zwMenuItem from '../../../component/navigation/menu/zwMenuItem.vue';
import zwSubMenu from '../../../component/navigation/menu/zwSubMenu.vue';
import zwMenuItemGroup from '../../../component/navigation/menu/zwMenuItemGroup.vue';
import zwCol from '../../../component/layout/zwCol.vue';
export default {
    components:{zwButton,zwIcon,zwBox,zwRow,zwCol,zwDropDown,zwMenu,zwMenuItem,zwMenuItemGroup,zwSubMenu,zwButtonGroup},
    data () {

        return {
            msg: 'Hello World!',
            loading:false,
        }
    }
    ,
    computed: {
        getLoadingVal:function(){
            return this.loading;
        }
    },
    methods:{
        changeLoading:function(){
             console.log("123");
          this.loading=!this.loading;
            // this.loading={delay:5};
              console.log(this.loading);
        }
    }
}
</script>
<style scoped>

</style>
