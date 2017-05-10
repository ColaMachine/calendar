var _Entity_ = function(state, divId) {
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
                <table >
                    <td  className="border-blue minH21  dropable">shangge1</td>
                    <td className="border-blue  minH21 dropable">shangge2</td>
                    <td className="border-blue minH21  dropable">shangge3</td>
                    <td className="border-blue  minH21 dropable">shangge4</td>
                </table>
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



