/*
var EntityStatck={
    data:{},
    push:function(name,component){
       this. data[name]=component;
    },
    get:function(name){
        return this.data[name];
    },
};*/
var _Entity_ = function(state, divId) {
        var name="header";

                var _state = state || {
                        login_frame_show:"none",
                        fast_login_show:"none",
                        get_free_pkg_show:"none",
                        left_time_show:"none",
                        title:"清廉网",
                    };

                var HeaderReact= React.createClass({
                render:function(){
                   return ( <header className="indexWrap phone-header back-blue">         <nav>
                      <a id="login" style={{cursor:"pointer"}}>
                   <div id="loginBtn" className="header-left center-text row-3 left small-text">
                                 <img className="header-left-img" src="http://msp-img.51awifi.com/V1/img/header/wifi.png"/>
                                           <span className="header-left-span right-text" id="linkMsg"></span>
                                              </div>             </a>
                                                <div className="center-text row-4 left normal-text" style={{whiteSpace:"nowrap",textOverflow:"ellipsis",overflow: "hidden"}} id="merchantname">Yxy</div>
                                                       <a href="#/userCenter" id="userCenter">                 <div className="header-right center-text row-3 left small-text">

                                                            <img className="header-right-img right" src="http://msp-img.51awifi.com/V1/img/header/information.png"/>
                                                                </div>
                                                                   </a>
                                                                     </nav>
                                                                     </header>)
                }
                });



                var EntityReact = React.createClass({

                    getInitialState: function() {
                        return _state;
                    },

                 render: function() {

            return (
<div style={{position:"relative"}}>
{this.state.title}
            <HeaderReact/>
            </div>
            );
        },

componentDidMount:function(){

},
                getInitialState:function(){
            	 return _state
                },
    });

    // OtherReact 在这里定义

    /**
     * 制作页面时返回React对象
     * @returns {*}
     */
    function render() {
        return ReactDOM.render(<EntityReact />, document.getElementById(divId));
    }

    return {
        name:name,
        render: render
    }
};

  //EntityStatck.push("header","_Entity_");

