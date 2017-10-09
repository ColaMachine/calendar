
<template>
  <li class="zw-menu-item"><a></zwIcon><span class="zw-menu-item-text"><slot></slot></span></a></li>
</template>
<script type="text/javascript">
import zwIcon from "../../icon/zwIcon.vue"
export default {
        name: 'zwMenuItem',
        components: {zwIcon  },
        props:{

                   },
        data () {
            return {

            };
        },
        computed: {


        },
        mounted () {
        },
        watch:{
　　　　　　　　
　　　　　　},
        methods: {

        },
    };
</script>
