
<template>
  <zwLayout>
           <zwHeader class="bg-black " >
  <zwRow >
  <zwCol span=24 >
           <zwMenu  mode="horizontal" theme="white" >
                    <zwMenuItem key="mail">
                      <zwIcon type="home" />首页
                    </zwMenuItem>
                    <zwMenuItem key="app" disabled>
                      <zwIcon type="appstore" />功能2
                    </zwMenuItem>




         <zwSubMenu >
          <template slot="title">    <zwIcon type="appstore" />功能3</template>

                    <zwMenuItemGroup title="Item 1">
                      <zwMenuItem key="setting:1">功能3</zwMenuItem>
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


             <zwMenuItem>
                                <zwDropDown><zwButton slot="button" sizeNum="" >登录</zwButton>
                                            <div class="zw-menu" slot="menu" >
                                            <zwForm >
                                                <zwFormItem >
                                                    <zwInput  clear=true  label="用户名" placeholder="请输入用户名">


                                                    </zwInput>
                                                </zwFormItem>
                                                 <zwFormItem >
                                                    <zwInput icon="password"  clear=true type="password" placeholder="请输入密码"></zwInput>
                                                 </zwFormItem>

                                                   <zwFormItem >
                                                        <zwInput icon="code"   placeholder="请输入验证密码">
                                                            <span  slot="append"> <img style="width:100%" src="data:image/png;base64,/9j/4AAQSkZJRgABAgAAAQABAAD/2wBDAAgGBgcGBQgHBwcJCQgKDBQNDAsLDBkSEw8UHRofHh0a
                                                                                           HBwgJC4nICIsIxwcKDcpLDAxNDQ0Hyc5PTgyPC4zNDL/2wBDAQkJCQwLDBgNDRgyIRwhMjIyMjIy
                                                                                           MjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjL/wAARCAAaAFADASIA
                                                                                           AhEBAxEB/8QAHwAAAQUBAQEBAQEAAAAAAAAAAAECAwQFBgcICQoL/8QAtRAAAgEDAwIEAwUFBAQA
                                                                                           AAF9AQIDAAQRBRIhMUEGE1FhByJxFDKBkaEII0KxwRVS0fAkM2JyggkKFhcYGRolJicoKSo0NTY3
                                                                                           ODk6Q0RFRkdISUpTVFVWV1hZWmNkZWZnaGlqc3R1dnd4eXqDhIWGh4iJipKTlJWWl5iZmqKjpKWm
                                                                                           p6ipqrKztLW2t7i5usLDxMXGx8jJytLT1NXW19jZ2uHi4+Tl5ufo6erx8vP09fb3+Pn6/8QAHwEA
                                                                                           AwEBAQEBAQEBAQAAAAAAAAECAwQFBgcICQoL/8QAtREAAgECBAQDBAcFBAQAAQJ3AAECAxEEBSEx
                                                                                           BhJBUQdhcRMiMoEIFEKRobHBCSMzUvAVYnLRChYkNOEl8RcYGRomJygpKjU2Nzg5OkNERUZHSElK
                                                                                           U1RVVldYWVpjZGVmZ2hpanN0dXZ3eHl6goOEhYaHiImKkpOUlZaXmJmaoqOkpaanqKmqsrO0tba3
                                                                                           uLm6wsPExcbHyMnK0tPU1dbX2Nna4uPk5ebn6Onq8vP09fb3+Pn6/9oADAMBAAIRAxEAPwD3+uY8
                                                                                           Ua7qujeHdYubG2t5dRsk+0QJKCElgBUu+Nw5VSwIBzlQcDcoretrvz57mB08uWB8Fc53KRlWHsRk
                                                                                           f7ysOcZqHV9PXUrB4tkTyqGaJZhujZipUq4wcqQzKRjoxxg4IcWk02NbmJZ+M4Jfhoni2UwttsTN
                                                                                           IgYxI0yjDRgtkjMgKDrnjGe+BrXxH1SDwJoetafpcMOp6tc+XFp9xuldo/nAZANrNnEZBAx8465B
                                                                                           ryywuNb1ewsPhYJGilj1aZbllAwkSckAhhvCt5zkHqQmD2r0D4taVa3et+BtH2eRZzXL2u2ABfLj
                                                                                           LQrhRjAwOnGK1xUFTjpu3+BsoRUkmXrD4g+KNL8S6fo/jDw9Dbf2lIkdtNaNwpJ28/MwbkrkAgqD
                                                                                           nByBWZP8QvH93421fw7ouhaZPLZSyFUmRo3MIcBXJaVQchkPHXORxWdpvhHRfBHxRs7LWY7m6trt
                                                                                           kfR7rLKscwcbVkCgbmBCjIOPmBK4b5a+v+D4/Gvxo8Uaa1y1vLHYxzwOOV8wRwKAw/u4Y9Oen0Kw
                                                                                           dnzc/QclG+ho3Pxa8UWGj6mL7TNKj1XT9QhtpVRjJFtkjlYr8rn5lMXPzHqQQCK1/EPxF1OH4WW+
                                                                                           s28SWeszXf2KdFwTayqWL5RgcEhPutyA45OOfPo9PfTfh34l0HVPDcltf6bcxT/2kitskfeibGbO
                                                                                           CwSYlQOCrk4B5bsdK0PWNW8XaLJcxzXGg6lpv9pX4SLyYRPNamGQkrgM7HB45HmEgDkmMSrVWo7a
                                                                                           Hn1nJVHGPW3/AA52XgrxDfah8PdL1fUpPtV3cTeVI+FTO64MQOFGOAR25x+NdhXk3gKTVtL8AJZa
                                                                                           5ZXNnDBqkQge4iMflp5sTncCAQuS5DHIzkZGFB9ZqI7GtNtwVzN1H/Q7mDU14RP3Vz/1yJ4Y/wC4
                                                                                           2DknCqZD3rSoopljRIhlaIOpkUBmTPIBzgkehwfyNOrN1H5dS0h14Y3LxkjqVMMjFfplVOPVR6Vp
                                                                                           UAVb/TbDVIFg1CytruFW3iO4iWRQ2CM4IPOCeferVFFAEDWqNdpdKWSVRtYr0kXnCt6gE5Hcc4OC
                                                                                           QZ6KKACq1pafY98cb/6Nx5UWP9V6gH+70wO3IHGALNFAH//Z" ></img></span>
                                                        </zwInput>
                                                  </zwFormItem>

                                                <zwFormItem><zwButton style="width:100%" type="primary" :loading_delay=5 sizeNum="large">登录</zwButton></zwFormItem>
                                            </zwForm>


                                            </div>
                                        </zwDropDown>

                            </zwMenuItem>
          </zwMenu>

          </zwCol>

            </zwRow>
           </zwHeader>
              <zwLayout class="zw-layout-has-sider ">
                                          <zwSider >




  <zwMenu mode="inline">
         <zwMenuItem key="grid">
                                    <zwIcon type="mail" /><a href="#api">api</a>
                                  </zwMenuItem>
                          <zwMenuItem key="grid">
                            <zwIcon type="mail" /><a href="#layout">layout</a>
                          </zwMenuItem>
                          <zwMenuItem key="grid" disabled>
                            <zwIcon type="appstore" /><a href="#grid">grid</a>
                          </zwMenuItem>
                            <zwMenuItem key="grid" disabled>
                                                      <zwIcon type="appstore" /><a href="#input">输入框</a>
                                                    </zwMenuItem>

                            <zwMenuItem key="grid" disabled>
                                                     <zwIcon type="appstore" /><a href="#button">button</a>
                                                   </zwMenuItem>
    <zwSubMenu  >
                        <template slot="title">    <zwIcon type="search" />data display</template>


                                    <zwMenuItem key="setting:1"><a href="#collapse">collapse</a></zwMenuItem>
                                    <zwMenuItem key="setting:2">Option 2</zwMenuItem>


                                </zwSubMenu>

<zwSubMenu  >
                        <template slot="title">    <zwIcon type="search" />Navigation</template>


                                    <zwMenuItem key="setting:1"><a href="#dropdownmenu">DropDown 下拉菜单</a></zwMenuItem>
                                    <zwMenuItem key="setting:2"><a href="#menu">menu 导航菜单</a></zwMenuItem>


                                </zwSubMenu>



                        </zwMenu>



                                          </zwSider>
                                           <zwContent>  <div id="router">

                                                                  <!-- 路由出口 -->
                                                                  <!-- 路由匹配到的组件将渲染在这里 -->
                                                                  <router-view></router-view>
                                                                  </div></zwContent>
                                            </zwLayout>

           <zwFooter>Footer</zwFooter>


     </zwLayout>
</template>
<script>


import zwRow from '../../component/layout/zwRow.vue';

import zwCol from '../../component/layout/zwCol.vue';
import zwIcon from '../../component/icon/zwIcon.vue';
import zwBox from '../../component/layout/zwBox.vue';
import zwButton from '../../component/button/zwButton.vue';
import zwLayout from '../../component/layout/zwLayout.vue';
import zwHeader from '../../component/layout/zwHeader.vue';
import zwContent from '../../component/layout/zwContent.vue';
import zwSider from '../../component/layout/zwSider.vue';
import zwFooter from '../../component/layout/zwFooter.vue';

import zwCollapse from '../../component/datadisplay/zwCollapse.vue';
import zwPanel from '../../component/datadisplay/zwPanel.vue';

import apiPath from '../../component/datadisplay/apiPath.vue';

import zwMenu from '../../component/navigation/menu/zwMenu.vue';
import zwMenuItem from '../../component/navigation/menu/zwMenuItem.vue';
import zwSubMenu from '../../component/navigation/menu/zwSubMenu.vue';
import zwMenuItemGroup from '../../component/navigation/menu/zwMenuItemGroup.vue';
import zwDropDown from '../../component/navigation/menu/zwDropDown.vue';
import zwForm from '../../component/dataentry/zwForm.vue';
import zwFormItem from '../../component/dataentry/zwFormItem.vue';
import zwInput from '../../component/dataentry/zwInput.vue';
export default {
    components:{zwRow,zwCol,zwIcon,zwBox,zwHeader,zwContent,zwFooter,zwLayout,zwSider,zwCollapse,zwPanel,apiPath,
       zwButton,zwMenu,zwMenuItem,zwMenuItemGroup,zwSubMenu,zwDropDown
       ,zwInput,zwFormItem,zwForm},
    data () {

        return {
            msg: 'Hello World!'
        }
    }
}
</script>
<style scoped>
#example {
    background: red;
    height: 100vh;
}
</style>
