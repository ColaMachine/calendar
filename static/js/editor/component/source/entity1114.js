var Entity1114 = function(state, divId) {
    var _state = state || {
        login_frame_show:"none",
        fast_login_show:"none",
        get_free_pkg_show:"none",
        left_time_show:"none123",
    };

    var EntityReact = React.createClass({
        getInitialState: function() {
            return _state;
        },
         render: function() {
            return (
                <ul className="app-li">
                    <li className="app-li-it">
                        <div className="app-li-it-pic"><a href="http://www.baidu.com"><img src="/static/phone/img/carousel/1.png"/></a></div>
                        <div className="app-li-it-content">
                            <div className="app-li-it-title"><a href="http://www.baidu.com"><h2>标题</h2></a></div>
                            <div className="app-li-it-text"><span className="src-net">淘宝|23:12</span><span className="comment-num">评论:2</span><span className="goods-score">评分:98%</span></div>
                        </div>
                    </li>
                    <li className="app-li-it">
                        <div className="app-li-it-pic"><a href="http://www.baidu.com"><img src="/static/phone/img/carousel/1.png"/></a></div>
                        <div className="app-li-it-content">
                            <div className="app-li-it-title"><a href="http://www.baidu.com"><h2>标题</h2></a></div>
                            <div className="app-li-it-text"><span className="src-net">淘宝|23:12</span><span className="comment-num">评论:2</span><span className="goods-score">评分:98%</span></div>
                        </div>
                    </li>
                    <li className="app-li-it">
                        <div className="app-li-it-pic"><a href="http://www.baidu.com"><img src="/static/phone/img/carousel/1.png"/></a></div>
                        <div className="app-li-it-content">
                            <div className="app-li-it-title"><a href="http://www.baidu.com"><h2>标题</h2></a></div>
                            <div className="app-li-it-text"><span className="src-net">淘宝|23:12</span><span className="comment-num">评论:2</span><span className="goods-score">评分:98%</span></div>
                        </div>
                    </li>
                    <li className="app-li-it">
                        <div className="app-li-it-pic"><a href="http://www.baidu.com"><img src="/static/phone/img/carousel/1.png"/></a></div>
                        <div className="app-li-it-content">
                            <div className="app-li-it-title"><a href="http://www.baidu.com"><h2>标题</h2></a></div>
                            <div className="app-li-it-text"><span className="src-net">淘宝|23:12</span><span className="comment-num">评论:2</span><span className="goods-score">评分:98%</span></div>
                        </div>
                    </li>
                    <li className="app-li-it">
                        <div className="app-li-it-pic"><a href="http://www.baidu.com"><img src="/static/phone/img/carousel/1.png"/></a></div>
                        <div className="app-li-it-content">
                            <div className="app-li-it-title"><a href="http://www.baidu.com"><h2>标题</h2></a></div>
                            <div className="app-li-it-text"><span className="src-net">淘宝|23:12</span><span className="comment-num">评论:2</span><span className="goods-score">评分:98%</span></div>
                        </div>
                    </li>
                </ul>
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



