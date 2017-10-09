
<template>
  <ul :class="getClasses"><slot></slot></ul>
</template>
<script type="text/javascript">
export default {
        name: 'zwMenu',
        components: {  },
        props:["mode"],
        data () {
            return {
            };
        },
        computed: {
            getClasses:function(){
                var classes = "zw-menu fnt-1  color-black  ";
                if(this.mode=="horizontal"){
                    classes+= " zw-menu-horizontal";
                }
                return classes;
            }
        },
        mounted () {
        },
        watch:{
　　　　　　　　
　　　　　　},
        methods: {

        },
    };
</script>
