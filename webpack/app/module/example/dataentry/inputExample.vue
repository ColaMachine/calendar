<template>
<div>
<article>
<section>
<h2>Input输入框</h2>
<p>通过鼠标或键盘输入内容，是最基础的表单域的包装。</p>
<h3>何时使用</h3>
<p>需要用户输入表单域内容时。
   提供组合型输入框，带搜索的输入框，还可以进行大小选择。</p>
<h3>代码演示<zwIcon type="windows" title="展开全部代码"></zwIcon></h3>
<zwRow >
<zwCol span=12>


    <zwBox class="">
        <div slot="zwCodeBoxDemo">
            <zwInput placeholder="Basic usage"></zwInput>

        </div>
        <div slot="zwCodeBoxMeta"  title="基本使用">
           基本使用
        </div>
        <div slot="zwCodeBoxPanel"  >

            <textarea ref="textarea" id="textarea" name="textarea">
           <zwInput placeholder="Basic usage"></zwInput>
             </textarea>

       </div>
    </zwBox>

    <zwBox class="">
            <div slot="zwCodeBoxDemo">
               <zwInput placeholder="Basic usage">
                <span slot="prepend">http:</span>
                <span slot="append">.com</span>
               </zwInput>



            </div>
             <div slot="zwCodeBoxMeta"  title="前置后置说明">

               input 可以加前置 后置
               用于配置一些固定组合。样式上采用display:table display:table-cell 并且vertical-align:center可解决;

            </div>
            <div slot="zwCodeBoxPanel"  >

                <textarea ref="textarea" id="textarea" name="textarea">
                <zwInput placeholder="Basic usage">
                               <span slot="prepend">http:</span>
                               <span slot="append">.com</span>
                              </zwInput>
                </textarea>

            </div>
        </zwBox>

        <zwBox class="">
            <div slot="zwCodeBoxDemo">

                <zwInput placeholder="Basic usage">
                                               <zwIcon slot="prefix" type="windows"></zwIcon>
                                               <zwIcon slot="suffix" type="windows"></zwIcon>
                                              </zwInput>
            </div>
             <div slot="zwCodeBoxMeta"  title="前缀和后缀">

              在输入框上添加前缀或后缀图标。
            </div>
            <div slot="zwCodeBoxPanel"  >

                <textarea ref="textarea" id="textarea" name="textarea">
                   <zwButton type="dashed" shape="circle" icon="search" loading :loading_delay=5></zwButton>
                   <zwButton type="dashed"  icon="search"loading  >search</zwButton>
                   <zwButton type="danger" shape="circle" icon="search" :loading="getLoadingVal"  v-on:click.native="changeLoading" :loading_delay=5></zwButton>
                   <zwButton type="danger"  icon="search" :loading="getLoadingVal" v-on:click.native="changeLoading" >search</zwButton>
                   export default {
                       components:{zwButton,zwIcon,zwBox,zwRow,zwCol},
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
                </textarea>

            </div>
        </zwBox>
        <!---------按钮组--------------->
        <zwBox >
            <div slot="zwCodeBoxDemo">
                <zwButtonGroup>
                    <zwButton type="dashed"  icon="search" loading :loading_delay=5></zwButton>
                    <zwButton type="dashed"  icon="search"loading  >search</zwButton>
                    <zwButton type="danger"  icon="search" :loading="getLoadingVal"  v-on:click.native="changeLoading" :loading_delay=5></zwButton>
                    <zwButton type="danger"  icon="search" :loading="getLoadingVal" v-on:click.native="changeLoading" >search</zwButton>
                </zwButtonGroup>
            </div>
             <div slot="zwCodeBoxMeta"  title="等待按钮">

                按钮有大、中、小三种尺寸。
                通过设置 size 为 large small 分别把按钮设为大、小尺寸。若不设置 size，则尺寸为中。
            </div>
            <div slot="zwCodeBoxPanel"  >

                <textarea ref="textarea" id="textarea" name="textarea">
                   <zwButton type="dashed" shape="circle" icon="search" loading :loading_delay=5></zwButton>
                   <zwButton type="dashed"  icon="search"loading  >search</zwButton>
                   <zwButton type="danger" shape="circle" icon="search" :loading="getLoadingVal"  v-on:click.native="changeLoading" :loading_delay=5></zwButton>
                   <zwButton type="danger"  icon="search" :loading="getLoadingVal" v-on:click.native="changeLoading" >search</zwButton>

                </textarea>

            </div>
        </zwBox>
</zwCol>



<zwCol span=12>
    <zwBox class="">
         <div slot="zwCodeBoxDemo">

            <zwButton type="primary" shape="circle" icon="search"></zwButton>
            <zwButton type="primary"  icon="search">search</zwButton>

            <zwButton  shape="circle" icon="search"></zwButton>
            <zwButton   icon="search">search</zwButton>

            <br />

            <zwButton type="dashed" shape="circle" icon="search"></zwButton>
            <zwButton type="dashed"  icon="search">search</zwButton>

            <zwButton type="danger" shape="circle" icon="search"></zwButton>
            <zwButton type="danger"  icon="search">search</zwButton>




        </div>.
         <div slot="zwCodeBoxMeta"  title="图标按钮">

          当需要在 Button 内嵌入 Icon 时，可以设置 icon 属性，或者直接在 Button 内使用 Icon 组件。
          如果想控制 Icon 具体的位置，只能直接使用 Icon 组件，而非 icon 属性。
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




    <zwBox class="">
             <div slot="zwCodeBoxDemo">

                <zwButton type="primary"  shape="circle" icon="search" disabled></zwButton>
                <zwButton type="primary"  disabled icon="search">search</zwButton>

                <zwButton  shape="circle" icon="search" disabled ></zwButton>
                <zwButton   icon="search" disabled >search</zwButton>

            </div>.
             <div slot="zwCodeBoxMeta"  title="disabled禁用">

                To mark a button as disabled, add the disabled property to the Button.
            </div>
             <div slot="zwCodeBoxPanel"  >

                <textarea ref="textarea" id="textarea" name="textarea">
                        <zwButton type="primary"  shape="circle" icon="search" disabled></zwButton>
                        <zwButton type="primary"  disabled icon="search">search</zwButton>

                        <zwButton  shape="circle" icon="search" disabled ></zwButton>
                        <zwButton   icon="search" disabled >search</zwButton>
                 </textarea>

           </div>
        </zwBox>


        <zwBox class="">
             <div slot="zwCodeBoxDemo">
                <zwDropDown>
                    <zwButton slot="button" type="primary"   icon="down" >下拉查询菜单</zwButton>
                    <zwMenu slot="menu">
                        <zwMenuItem key="1">1st item</zwMenuItem>
                        <zwMenuItem key="2">2nd item</zwMenuItem>
                        <zwMenuItem key="3">3rd item</zwMenuItem>
                    </zwMenu>
                </zwDropDown>
            </div>



             <div slot="zwCodeBoxMeta"  title="多按钮 下拉">

                To mark a button as disabled, add the disabled property to the Button.
            </div>
             <div slot="zwCodeBoxPanel"  >

                <textarea ref="textarea" id="textarea" name="textarea">
                        <div slot="zwCodeBoxDemo">
                                       <zwDropDown>
                                           <zwButton slot="button" type="primary"   icon="down" >下拉查询菜单</zwButton>
                                           <zwMenu slot="menu">
                                               <zwMenuItem key="1">1st item</zwMenuItem>
                                               <zwMenuItem key="2">2nd item</zwMenuItem>
                                               <zwMenuItem key="3">3rd item</zwMenuItem>
                                           </zwMenu>
                                       </zwDropDown>
                                   </div>
                 </textarea>

           </div>
        </zwBox>
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
import zwInput from '../../../component/dataentry/zwInput.vue';

import zwCol from '../../../component/layout/zwCol.vue';
export default {
    components:{zwButton,zwIcon,zwBox,zwRow,zwCol,zwDropDown,zwMenu,zwMenuItem,zwButtonGroup,zwInput},
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
