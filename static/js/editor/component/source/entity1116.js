var Entity1116= function(state, divId) {
    var name="header";

    var _state = state || {
    };
var EntityReact = React.createClass({
    getInitialState: function() {
        return _state;
    },

    render: function() {
    return (
        <div className="slider">
    <ul>
        <li><a href="http://www.internetke.com" target="_blank"><img src="http://www.internetke.com/jsEffects/2014052304/images/1.jpg" alt="科e互联网站建设团队"/></a></li>
        <li><a href="http://www.internetke.com" target="_blank"><img src="http://www.internetke.com/jsEffects/2014052304/images/2.jpg" alt="网页特效集锦"/></a></li>
        <li><a href="http://www.internetke.com" target="_blank"><img src="http://www.internetke.com/jsEffects/2014052304/images/3.jpg" alt="JS代码素材库"/></a></li>
        <li><a href="http://www.internetke.com" target="_blank"><img src="http://www.internetke.com/jsEffects/2014052304/images/4.jpg" alt="用心建站用心服务"/></a></li>
        <li><a href="http://www.internetke.com" target="_blank"><img src="http://www.internetke.com/jsEffects/2014052304/images/5.jpg" alt="学会用才能学会写"/></a></li>
    </ul>
</div>
    );
    },

    componentDidMount:function(){
          // $(".slider").yxMobileSlider({width:640,height:320,during:3000})
             $('.slider').yxMobileSlider({width:640,height:320,during:3000})
        },
        getInitialState:function(){
            return _state
        },
    });
    /**
     * 制作页面时返回React对象
     * @returns {*}
     */
   var react =null;
       function render() {
            react=  ReactDOM.render(<EntityReact />, document.getElementById(divId));
            return react;

       }
       function setState(state_){
           for(var i in state_){
                _state[i]=state_[i];
           }
           react.setState(_state);
      }
       return {
           name:name,
           render: render,
           setState:setState,
           state:_state
       }
};

