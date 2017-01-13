var EditorHead = React.createClass({
	getInitialState: function() {
	    return {

	    };
	  },

	  render: function() {
		  return (
		  <header className=
		  "header">
            333
		  </header>
        );
        }
    }
);


var EditorCanvas = React.createClass({
	getInitialState: function() {
	    return {
            titleEdit:false,
            title:"页面1"
	    };
	  },

	  render: function() {

		  return (
            <div className="canvas-area col-xs-11 col-sm-7">
                <div className="mask-left"></div>
                <div className="mask-right"></div>
                <div className="canvas-viewport">
                    <div className="canvas-window">
                        <div className="mask-top"> </div>
                        <div className="mask-bottom"></div>
                        <div className="canvas-animation">
                            <div className="bg-layer dropable selected"></div>
                            <div className="elements-layer"></div>
                        </div>

                    </div>


                </div>

            </div>

        );
        }
    }
);


var EditorMain = React.createClass({
	getInitialState: function() {
	    return {

	    };
	  },
    preView:function(){
        var templateId  =  $(".pageActive>.tab-pane").filter(".in")[0].id;
        window.open("/static/html/editor/index.html?id="+templateId);
    },
    saveCurrentTemplate:function(){
        //console.log("当前选择模板长度:"+$(".pageActive>.tab-pane").filter(".in").length);
        console.log("当前选择模板:"+$(".pageActive>.tab-pane").filter(".in")[0].id);
        var templateId  =  $(".pageActive>.tab-pane").filter(".in")[0].id;

        var components ={templateId:templateId};
var data = [];
var i=0;
       $(".dropable>div").each(function(){
            var divId = $(this).attr("id");
            if(globalEntity[divId]!=null){
                 var componentsInstance={};
                 componentsInstance.index=i;
                 i++;
                 componentsInstance.id=divId;
                // componentsInstance.componentId=globalEntity[divId].templateId;
                 componentsInstance.componentId=globalEntity[divId].componentId;
                 //alert(divId);
                 if(divId.indexOf("component")>=0){
                    componentsInstance.id=null;
                    //alert("新家的");
                 }
                 console.log("_state");
                              console.log(globalEntity[divId].entity);
                 var state =globalEntity[divId].entity.state;
                 console.log("组件id"+componentsInstance.id);
                   // console.log("state:"+json2Str(state));
                    componentsInstance.state= JSON.stringify(state);
                    //components[componentsInstance.id]= json2Str(state);
//                 components.push(componentsInstance);
                data.push(componentsInstance);

            }

        });
        console.log(data);
        components.data=JSON.stringify(data);

        Ajax.post("/editorTempComp/msave1.json",components,function(result){
                            if(result.r==AJAX_SUCC){
                                alert("保存成功");
                            }
                        });

    },
	  render: function() {
          var that =this;
		  return (
              <div className="container-fluid">
                  <div className="row body-container">
                      <div className="col-sm-12">
                            <EditorHead/>
                            <div className="left-container">
                                <PageNav/>
                                <input type='button' value='保存' onClick={this.saveCurrentTemplate}/>
                                 <input type='button' value='预览' onClick={this.preView}/>
                                <EditorCanvas/>
                            </div>
                            <RightBar  />
                      </div>
                  </div>

              </div>

        );
        }
    }
);

var PageNav = React.createClass({
	getInitialState: function() {
	    return {
            arr:[{key:"1",name:"页面",content:<EditTemplateWrap/>,active:true},{key:"2",name:"图层",content:"hello"}]
	    };
	  },
    selectTab:function(data){
      for(var i in this.state.arr){
          if(this.state.arr[i].name!= data){
              this.state.arr[i].active=false;
          }else{
              this.state.arr[i].active=true;
          }
      }
        this.setState(this.state);
    },
	  render: function() {
          var that =this;
		  return (
          <div className="page-navigator ">
              <div className="mask"></div>
              <div>
                  <ul className="nav nav-tabs">
                    {
                      this.state.arr.map(function(result,index){
                          var data = result.name;
                          return  <li key={index} className={ result.active ? 'active' : '' } >
                              <a href="#"  onClick={that.selectTab}>{result.name}</a>
                          </li>;
                      })
                    }
                  </ul>
                  <div className="tab-content">
                    {
                        this.state.arr.map(function(result,index){
                            return <div   key={index} className={ result.active ? 'tab-pane fade in  active ' : 'tab-pane fade ' } >
                              {result.content}
                            </div>;
                        })
                    }
                  </div>
              </div>
          </div>

        );
        }
    }
);



function addComponent(componentId,tempCompId,html,config){

    if($("#script_entity_"+componentId).length==0){

        if(StringUtil.isBlank(html)){
            $(document).append("<script id='script_entity_"+componentId+"' type='text/javascript' src='"+"/static/js/editor/component/source/entity"
            +componentId+".js"+"'  charset='utf-8'> </script>");
            $(document).append("<script id='script_setting_"+componentId+"' type='text/javascript' src='"+"/static/js/editor/component/source/setting"
            +componentId+".js"+"'  charset='utf-8'> </script>");
        }
          // $(document).append("<link rel='stylesheet' type='text/css' href='"+"/tmp/component"+componentId+".css'   id='css_"+componentId+"' />");
            //document.write("<link rel='stylesheet' href='"+data[i]+"' type='text/css' />");
            loadStyles("/tmp/component"+componentId+".css");
    }

   // console.log("div");
    var div = document.createElement("div");
    //var entity =  _Entity_(null,document.getElementById("header"));
    //
    var newFlag = false;
    if(StringUtil.isBlank(tempCompId)){
        newFlag=true;
    }
 div.className="component-wrap";
    if(newFlag){
        document.body.appendChild(div);

        tempCompId=   "component_instance_"+componentId+"_"+(Math.floor(Math.random()*100));
        Drag.dragStart({
            target:div,
            copy:false,
            dest:".dropable",
            start:function(){},
            dragging:function(x,y){},
            end:function(x,y){},
            }
        );
    }else{
         $(".dropable").append(div);

    }
    div.id= tempCompId;


    //div.id=   "component_instance_"+componentId+"_"+(Math.floor(Math.random()*100));

    // var  contanerId = "component_instance_container_"+componentId+"_"+(Math.floor(Math.random()*100));
    var  containerId = "component_instance_container_"+componentId+"_"+(Math.floor(Math.random()*100));//"+tempCompId;//componentId+"_"
    div.innerHTML="<div class='float-toolbar'><ul><li><a title='修改'><i class='fa fa-pencil'></i></a></li><li><a class='delBtn' title='删除'><i class='fa fa-trash'></i></a></li><li><a class='moveUp' title='上移'>上移<i class='fa fa-trash'></i></a></li><li><a class='moveDown' title='下移'>下移<i class='fa fa-trash'></i></a></li></ul></div><div class='component-container' id ="+containerId+"></div>";

    // console.log( $(div).find(".delBtn").length);
    $(div).find(".delBtn").click({id:div.id},function(event){
        //console.log("删除"+event.data.id);
        $("#"+event.data.id).remove();
    });
     $(div).find(".moveUp").click({id:div.id},function(event){
        var dom =$("#"+event.data.id);
        if($(dom).prev()){  console.log("up"+event.data.id);
            $(dom).after( $(dom).prev());
        }
    });
     $(div).find(".moveDown").click({id:div.id},function(event){
      //  console.log("down"+event.data.id);
        var dom =$("#"+event.data.id);
        if($(dom).next().length>0){
            $(dom).next().after($(dom));
        }
    })

    //console.log("初始化setting"+result.data[i].config);
    // console.log(eval('('+result.data[i].config+')'));
/*     Ajax.get(PATH+"/static/html/editor/source/entity"+componentId+".html", null, function(data) {


                    $("#"+containerId).html(result);
                    //$('.main').append(ibox.render(data,"新窗口"));
                    //if (typeof fun == 'function') fun();
                });*/
    if(!StringUtil.isBlank(html)){
    // Ajax.getJSON("/static/html/editor/source/entity"+componentId+".html")
        $("#"+containerId).html(html);
           globalEntity[div.id]={componentId:componentId,entity:{}} ;
        return;
    }
    /* if(StringUtil.isBlank(entity)){

    }*/

     var entityClass = eval("Entity"+componentId);
     if(!StringUtil.isBlank(config)){
        config = eval('('+config+')');
     }else{
        config=null;
     }

     console.log("containerId:"+containerId);
     console.log($("#"+containerId).length);
    var entityInstance=   entityClass.call(this,null,containerId);
    //
    // console.log("渲染"+containerId);
    // // var entity = eval("component"+id+"(null,Drag.tdiv.id)");// _Entity_(null,Drag.tdiv.id);

    var entityReact= entityInstance;
    entityInstance.render();
      console.log("渲染结束");
    // console.log(entityReact);;
     // console.log("渲染结束");
     globalEntity[div.id]={componentId:componentId,entity:entityReact} ;
    var config = eval("Setting"+componentId);
    var configInstance=   config.call(this,entityReact,"config-wrap");
    div.onclick=function(){
        $(".component-select").removeClass("component-select");
        $(this).addClass("component-select");
        configInstance.render();
    }
}
var EditTemplateWrap = React.createClass({
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
         $(".dropable").empty();//清空其他模板的内容

console.log("后台获取的参数个数"+result.data.length);
            for(var i=0;i<result.data.length;i++){
             console.log("后台获取的参数");
                console.log(result.data[i]);
                var id = result.data[i].id;
                var componentId = result.data[i].componentId;

                addComponent(componentId,id,result.data[i].html,result.data[i].config);
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
              page_title= <span style={hideStyle}  ref="title" >{this.state.title}</span>;
              page_title_block=<input type="text" value={this.state.title}  ref="titleInput" onBlur={this.hideTitleEdit} onChange={this.handleTitleChange}></input>;
          }else{
              page_title= <span   ref="title" >{this.state.title}</span>;
              page_title_block=<input  style={hideStyle} type="text" value={this.state.title}  ref="titleInput" onBlur={this.hideTitleEdit} onChange={this.handleTitleChange}></input>;
          }
		  return (
            <div>
              <div className="page-reorder">
                  <ul className="sortable">
                      <li className="page-menu pageActive">

                        {
                          this.state.template.map(function(result,index){
                              return <div   onClick={this.selectTemplate.bind(this,result.id)} key={index} id={result.id} className={ result.active ? 'tab-pane fade in  active ' : 'tab-pane fade ' } >
                                 <PagePreView/>
                              </div>;
                          }.bind(this))
                      }

                      </li>
                  </ul>
              </div>
              <div className="add-blank-page">+</div>
         </div>

        );
        }
    }
);


var PagePreView = React.createClass({
	getInitialState: function() {
	    return {
            titleEdit:false,
            title:"页面1"
	    };
	  },
      showTitleEdit:function(){
//          React.findDOMNode(this.refs.title).hide();
//          React.findDOMNode(this.refs.titleInput).show();
           ReactDOM.findDOMNode(this.refs.titleInput).style.display="block";
          ReactDOM.findDOMNode(this.refs.title).style.display="none";
          //this.setState({titleEdit:true});
          ReactDOM.findDOMNode(this.refs.titleInput).focus();
      },
    handleTitleChange:function(event){
        this.setState({title: event.target.value});

    },
    hideTitleEdit:function(){
        ReactDOM.findDOMNode(this.refs.titleInput).style.display="none";
        ReactDOM.findDOMNode(this.refs.title).style.display="block";
       // this.setState({titleEdit:false});
    },
	  render: function() {
           var title =this.state.title;
          var page_title ;
          var page_title_block;
          var hideStyle ={display:'none'};
          if(this.state.titleEdit){//显示input
              page_title= <span style={hideStyle}  ref="title" >{this.state.title}</span>;
              page_title_block=<input type="text" value={this.state.title}  ref="titleInput" onBlur={this.hideTitleEdit} onChange={this.handleTitleChange}></input>;
          }else{
              page_title= <span   ref="title" >{this.state.title}</span>;
              page_title_block=<input  style={hideStyle} type="text" value={this.state.title}  ref="titleInput" onBlur={this.hideTitleEdit} onChange={this.handleTitleChange}></input>;
          }
		  return (

                  <div className="page-thumb-block active" >
                      <div className="page-thumb" >
                          <div className="page-thumb-bg"  ></div>
                          <div className="page-thumb-elements" ></div>
                      </div>
                      <span className="page-num"   onClick={this.showTitleEdit}>
                        {page_title}{page_title_block}
                      </span>
                      <div className="icon-button icon-uninstall fa fa-trash" ></div>
                      <div className="icon-button icon-copy fa fa-copy" ></div>
                  </div>


        );
        }
    }
);


var globalEntity={};
var RightBar = React.createClass({
	getInitialState: function() {
	    return {
            components:[]
	    };
    },
    selectBlcok:function(component){
addComponent(component.id,"",component.html,null);return;
        var dom =document.getElementById(component.id);
       // document.appendChild(document.createElement("div"));
//   alert(document.getElementById("header"));
//      var entity =  _Entity_(null,document.getElementById("header"));
//                         entity.render();

    if($("#script_entity_"+component.id).length==0){
        $(document).append("<script id='script_entity_"+component.id+"' type='text/javascript' src='"+"/static/js/editor/component/source/entity"
        +component.id+".js"+"'  charset='utf-8'> </script>");
        $(document).append("<script id='script_setting_"+component.id+"' type='text/javascript' src='"+"/static/js/editor/component/source/setting"
        +component.id+".js"+"'  charset='utf-8'> </script>");
loadStyles("/tmp/component"+component.id+".css");
       // $(document).append("<link rel='stylesheet' type='text/css' href='"+"/tmp/css/component"+component.id+".css'   id='css_"+component.id+"' />");
    }


    var div = document.createElement("div");
    //var entity =  _Entity_(null,document.getElementById("header"));
    document.body.appendChild(div);
    div.className="component-wrap";
    div.id=   "component_instance_"+component.id+"_"+(Math.floor(Math.random()*100));

    var  containerId = "component_instance_container_"+component.id+"_"+(Math.floor(Math.random()*100));
      //div.innerHTML="<div class='float-toolbar'><ul><li><a title='修改'><i class='fa fa-pencil'></i></a></li><li><a class='delBtn' title='删除'><i class='fa fa-trash'></i></a></li></ul></div><div class='component-container' id ="+contanerId+"></div>";

    div.innerHTML="<div class='float-toolbar'><ul><li><a title='修改'><i class='fa fa-pencil'></i></a></li><li><a class='delBtn' title='删除'><i class='fa fa-trash'></i></a></li><li><a class='moveUp' title='上移'>上移<i class='fa fa-trash'></i></a></li><li><a class='moveDown' title='下移'>下移<i class='fa fa-trash'></i></a></li></ul></div><div class='component-container' id ="+containerId+"></div>";

    var entity = eval("Entity"+component.id);
    $(div).find(".delBtn").click(function(){

        $(div).remove();
         globalEntity[div.id]=null;

    })
    var entityInstance=   entity.call(this,null,containerId);

  // // var entity = eval("component"+id+"(null,Drag.tdiv.id)");// _Entity_(null,Drag.tdiv.id);
    var entityReact= entityInstance;entityInstance.render();
     globalEntity[div.id]={id:div.id,componentId:component.id,entity:entityReact} ;
        var config = eval("Setting"+component.id);
        var configInstance=   config.call(this,entityReact,"config-wrap");
        div.onclick=function(){
        $(".component-select").removeClass("component-select");
        $(this).addClass("component-select");
        configInstance.render();

    /*  Drag.dragStart({
                 target:this,
                 copy:false,
                 dest:".dropable",
                 start:function(){},
                 dragging:function(x,y){},
                 end:function(x,y){},
                 }
             );*/
     }


        console.log(div);
        Drag.dragStart({
            target:div,
            copy:false,
            dest:".dropable",
            start:function(){},
            dragging:function(x,y){},
            end:function(x,y){},
            }
        );
    },
    componentDidMount:function(){
    /* for(var i in EntityStatck){
                               var id = "addable-block_1"+i;
                               $(".right-bar").append(  <div id={id} className="addable-block dragable addable" onClick={this.selectBlcok}></div>

                                                                                )
                             <div id={id} className="addable-block dragable addable" onClick={this.selectBlcok}>
                                EntityStatck[i]
                            </div>
                        }*/

        this.request();
        Drag.init();

    /* for(var key in EntityStatck.data){
        alert(EntityStatck.data[key]+"(null,document.getElementById('"+key+"'))");
                var entity =eval(EntityStatck.data[key]+"(null,document.getElementById('"+key+"'))");

           entity.render();
                }*/
    },
    request:function(){
        Ajax.getJSON("/component/list.json",null,function(result){
        for( var i=0;i<result.data.length;i++){
            result.data[i].face="/"+ result.data[i].face;
        }
               // alert(result.data.length);
            this.setState({components:result.data});
        }.bind(this))
    },
    render: function() {

          // var results  ={"1":"1","2":"2"};
        var val = [];
/*            for(var key in this.state.EntityStatck.data){
            //console.log("key"+key);
                val.push({key:key,value:EntityStatck.get[key]});
            }

     return  <div key={index} className="addable-block dragable addable" onClick={this.selectBlcok.bind(this,{key})}>{key} </div>;
                              }.bind(this))}
            */
		  return (


            <div className="right-bar">
            <div id='config-wrap'className='config-wrap'>
                       		    </div>
                {this.state.components.map(function(component,index){
                     return  <div key={component.id} id={component.id} className="addable-block dragable addable" onClick={this.selectBlcok.bind(this,component)} >
                        <img src={component.face}/>{component.name}
                        <div>{component.name}</div> <div className="bottom-title-bar">{component.remark}</div>
                     </div>;
                  }.bind(this))}
            </div>


        );
        }
    }
);
