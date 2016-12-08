var EditTemplateWrap = React.createClass({displayName: "EditTemplateWrap",
	getInitialState: function() {
	    return {
            titleEdit:false,
            title:"页面1",
             template:[{id:"1",name:"页面",active:true}]
	    };
	  },
      showTitleEdit:function(){
        //React.findDOMNode(this.refs.title).hide();
        //React.findDOMNode(this.refs.titleInput).show();
        ReactDOM.findDOMNode(this.refs.titleInput).style.display="block";
        ReactDOM.findDOMNode(this.refs.title).style.display="none";
        //this.setState({titleEdit:true});
        ReactDOM.findDOMNode(this.refs.titleInput).focus();
      },
    handleTitleChange:function(event){
        this.setState({title: event.target.value});

    },
     componentDidMount:function(){
            Ajax.getJSON(PATH+"/template/list.json",{},function(result){

                if(result.r==AJAX_SUCC){
                    this.setState({template:result.data});
                }
            }.Apply(this));
          },
    hideTitleEdit:function(){
        ReactDOM.findDOMNode(this.refs.titleInput).style.display="none";
        ReactDOM.findDOMNode(this.refs.title).style.display="block";
       // this.setState({titleEdit:false});
    },
    selectTemplate:function(id){
        console.log(id);
        var template =this.state.template;
       for(var i=0;i<template.length;i++){
            if(template[i].id== id){
               template[i].active=true;
            }else{
             template[i].active=false;
            }
       }
       Ajax.getJSON("/editorTempComp/list.json?templateId="+id,null,function(result){
            for(var i=0;i<result.data.length;i++){
                var comonentId = result.data[i].componentId;
                  if($("#script_entity_"+comonentId).length==0){
                        $(document).append("<script id='script_entity_"+comonentId+"' type='text/javascript' src='"+"/tmp/entity"
                        +comonentId+".js"+"'  charset='utf-8'> </script>");
                        $(document).append("<script id='script_setting_"+comonentId+"' type='text/javascript' src='"+"/tmp/setting"
                        +comonentId+".js"+"'  charset='utf-8'> </script>");

                      // $(document).append("<link rel='stylesheet' type='text/css' href='"+"/tmp/component"+comonentId+".css'   id='css_"+comonentId+"' />");
                        //document.write("<link rel='stylesheet' href='"+data[i]+"' type='text/css' />");
loadStyles("/tmp/component"+comonentId+".css");
                    }

                     console.log("div");
                    var div = document.createElement("div");
                    //var entity =  _Entity_(null,document.getElementById("header"));
                     $(".dropable").empty();
                    $(".dropable").append(div);
                    div.className="component-wrap";
                    div.id=   "component_instance_"+comonentId+"_"+(Math.floor(Math.random()*100));
                    var  contanerId = "component_instance_container_"+comonentId+"_"+(Math.floor(Math.random()*100));
                    div.innerHTML="<div class='float-toolbar'><ul><li><a title='修改'><i class='fa fa-pencil'></i></a></li><li><a class='delBtn' title='删除'><i class='fa fa-trash'></i></a></li></ul></div><div class='component-container' id ="+contanerId+"></div>";
                    var entity = eval("Entity"+comonentId);
                    $(div).find(".delBtn").click(function(){
                        $(div).remove();
                    })
                    console.log("初始化setting"+result.data[i].config);
                     console.log(eval('('+result.data[i].config+')'));
                    var entityInstance=   entity.call(this,eval('('+result.data[i].config+')'),contanerId);
                    // // var entity = eval("component"+id+"(null,Drag.tdiv.id)");// _Entity_(null,Drag.tdiv.id);
                    var entityReact= entityInstance.render();
                     globalEntity[div.id]={id:comonentId,entity:entityReact} ;
                    var config = eval("Setting"+comonentId);
                    var configInstance=   config.call(this,entityReact,"config-wrap");
                    div.onclick=function(){
                        $(".component-select").removeClass("component-select");
                        $(this).addClass("component-select");
                        configInstance.render();
                    }
            }
       }.bind(this));
     this.setState({"template":template});
        //$("#"+id).addClass("in");
    },
	  render: function() {
           var title =this.state.title;
          var page_title ;
          var page_title_block;
          var hideStyle ={display:'none'};
          if(this.state.titleEdit){//显示input
              page_title= React.createElement("span", {style: hideStyle, ref: "title"}, this.state.title);
              page_title_block=React.createElement("input", {type: "text", value: this.state.title, ref: "titleInput", onBlur: this.hideTitleEdit, onChange: this.handleTitleChange});
          }else{
              page_title= React.createElement("span", {ref: "title"}, this.state.title);
              page_title_block=React.createElement("input", {style: hideStyle, type: "text", value: this.state.title, ref: "titleInput", onBlur: this.hideTitleEdit, onChange: this.handleTitleChange});
          }
		  return (
            React.createElement("div", null, 
              React.createElement("div", {className: "page-reorder"}, 
                  React.createElement("ul", {className: "sortable"}, 
                      React.createElement("li", {className: "page-menu pageActive"}, 

                        
                          this.state.template.map(function(result,index){
                              return React.createElement("div", {onClick: this.selectTemplate.bind(this,result.id), key: index, id: result.id, className:  result.active ? 'tab-pane fade in  active ' : 'tab-pane fade '}, 
                                 React.createElement(PagePreView, null)
                              );
                          }.bind(this))
                      

                      )
                  )
              ), 
              React.createElement("div", {className: "add-blank-page"}, "+")
         )

        );
        }
    }
);

