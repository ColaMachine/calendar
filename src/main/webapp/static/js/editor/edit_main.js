var EditorMain = React.createClass({displayName: "EditorMain",
	getInitialState: function() {
	    return {

	    };
	  },

    saveCurrentTemplate:function(){
        //console.log("当前选择模板长度:"+$(".pageActive>.tab-pane").filter(".in").length);
        console.log("当前选择模板:"+$(".pageActive>.tab-pane").filter(".in")[0].id);
        var templateId  =  $(".pageActive>.tab-pane").filter(".in")[0].id;

        var components ={templateId:templateId};

        var components =$(".dropable>div").each(function(){
            var divId = $(this).attr("id");
            if(globalEntity[divId]!=null){
                 var componentsInstance={};
                 componentsInstance.id=globalEntity[divId].id;
                 var state =globalEntity[divId].entity.state;
                 console.log("组件id"+componentsInstance.id);
                    console.log("state:"+json2Str(state));
                    components[componentsInstance.id]= json2Str(state);
//                 components.push(componentsInstance);
                Ajax.post("/editorTempComp/msave1.json",components,function(result){
                    if(result.r==AJAX_SUCC){
                        alert("保存成功");
                    }
                })

            }

        });

    },
	  render: function() {
          var that =this;
		  return (
              React.createElement("div", {className: "container-fluid"}, 
                  React.createElement("div", {className: "row body-container"}, 
                      React.createElement("div", {className: "col-sm-12"}, 
                            React.createElement(EditorHead, null), 
                            React.createElement("div", {className: "left-container"}, 
                                React.createElement(PageNav, null), 
                                React.createElement("input", {type: "button", value: "保存", onClick: this.saveCurrentTemplate}), 
                                React.createElement(EditorCanvas, null)
                            ), 
                            React.createElement(RightBar, null)
                      )
                  )

              )

        );
        }
    }
);
