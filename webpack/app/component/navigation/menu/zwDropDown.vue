
<template>
  <div   v-on:mouseenter="showMenu" v-on:mouseleave="hideMenu"   class="zw-dropdown" ><slot name="button" ></slot> <slot v-if="menuShow" name="menu"></slot> </div>
</template>
<script type="text/javascript">
export default {
        name: 'zwDropDown',
        components: {  },
        props:{

               },
        data () {
            return {
               menuShow:false,
            };
        },
        computed: {


        },
        mounted () {
        },
        watch:{
　　　　　　　
　　　　　　　　
　　　　　　},
        methods: {
            showMenu:function(){console.log("enter ");
                this.menuShow=true;
            },
            hideMenu:function(){console.log("leave ");
                this.menuShow=false;
            }
        },
    };
</script>
