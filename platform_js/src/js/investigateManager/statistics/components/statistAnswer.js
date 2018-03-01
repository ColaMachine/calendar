/**
 * Copyright(C),1988-1999,aWiFi Operations Center
 * Author:    作者 Stanley Huang   Version:1.0   Date:2018.1.10
 * Description: 选择题
 * Others:无
 */
import React from 'react';
import  Axios from 'axios';
import {Button, Table, Modal, Pagination, notification} from 'antd';
import AnswerOption from './itemAnswer'
import $http from 'util/http';
/**
 * 选择题统计Tab内容
 */
export  default  class StatSelect extends React.Component {
    constructor(p) {
        super(p);

        this.state = {
           list:[],
           total:0,
           questionDescription:'',
           id:0,
           yellow:'blue',
           mun: this.props.data[0].id
          
        };
        this.onPageSizeChange = this.onPageSizeChange.bind(this);
        this.onPageChange = this.onPageChange.bind(this);

        this.pageNo = 1;
        this.pageSize = 10;
      
    }
    componentWillMount() {
        this.search(this.props.data[0].id);
    }
    componentDidMount=()=>{
       let select=this.refs.boy.children;
       Array.from(select).map((item1,index)=>{
          Array.from(item1.children).map((item2,index)=>{
            let mun= item2.innerHTML;
                item1.onclick=()=>{
                    this.search(mun);
                    this.setState({mun});
                    this.props.data.map((item,index)=>{
                            if(mun==item.id){
                                let questionDescription=item.questionDescription;
                                let questionNumber=item.questionNumber;
                                let id=mun;
                                var url = 'questionnaire/statistics/answers';
                                url = url + '?'+`id=${mun}`+"&"+`pageNum=${this.pageNo}`+"&"+`pageSize=${this.pageSize}`+"&"+`type=1`;
                                Axios.get(url, this.HeaderToken).then(({data}) => {
                                let{list=[],total=0}=data.data;
                                list.forEach((item, index) => {
                                        item.key = index + this.pageSize * (this.pageNo - 1) + 1;
                                    })
                                    this.setState({
                                        list, total,questionDescription,id,questionNumber
                                    });
                                }).catch((err) => {
                                    
                                }) 
                               
                            }
                        })
                }
           })
       
       })
    }
   
    onPageSizeChange(current, size) {
        this.pageSize = size;
        this.search(this.state.id,this.state.type);
    }

    onPageChange(page, pageSize) {
        this.pageNo = page;
        this.search(this.state.id,this.state.type);
    }
    search=(mun,type)=>{
                let item=this.props.data[0]
                let questionDescription=item.questionDescription;
                let questionNumber=item.questionNumber;
                if(mun==item.id){
                    var url = 'questionnaire/statistics/answers';
                    url = url + '?'+`id=${item.id}`+"&"+`pageNum=${this.pageNo}`+"&"+`pageSize=${this.pageSize}`+"&"+`type=${type?type:1}`;
                    
                    Axios.get(url, this.HeaderToken).then(({data}) => {
                       let{list=[],total=0}=data.data;
                       list.forEach((item, index) => {
                            item.key = index + this.pageSize * (this.pageNo - 1) + 1;
                        })
                        let id=item.id;
                       this.setState({
                            list, total,questionDescription,id,questionNumber
    
                         });
                    }).catch((err) => {
                        
                    })
                }else{
                    var url = 'questionnaire/statistics/answers';
                    url = url + '?'+`id=${mun}`+"&"+`pageNum=${this.pageNo}`+"&"+`pageSize=${this.pageSize}`+"&"+`type=${type?type:1}`;
                    Axios.get(url, this.HeaderToken).then(({data}) => {
                       let{list=[],total=0}=data.data;
                       list.forEach((item, index) => {
                            item.key = index + this.pageSize * (this.pageNo - 1) + 1;
                        })
                        let id=mun;
                        console.log('vvvvvvvvvvvvvvvvvv',mun,this.props.data)
                        this.props.data.map(item=>{
                            if(item.id==mun){
                                this.setState({
                                    questionDescription:item.questionDescription,
                                    questionNumber:item.questionNumber
                                })
                            }
                        })
                       this.setState({
                            list, total,id
    
                         });
                    }).catch((err) => {
                        
                    })
                }    
        }
    GoodAdvice=()=>{
       var url = 'questionnaire/statistics/answers';
       url = url + '?'+`id=${this.state.mun}`+"&"+`pageNum=${this.pageNo}`+"&"+`pageSize=${this.pageSize}`+"&"+`type=2`;
       Axios.get(url, this.HeaderToken).then(({data}) => {
          let{list=[],total=0}=data.data;
          list.forEach((item, index) => {
               item.key = index + this.pageSize * (this.pageNo - 1) + 1;
           })
           let type=2;
          this.setState({
               list, total,type

            });
       }).catch((err) => {
           
       })
     }
     handle=(e)=>{
        let select=this.refs.boy.children;
        for(var i=0;i<select.length;i++){
            select[i].className="";
        }
        e.target.className="active"
     }
     pageItemRender(current, type, originalElement) {
        if (type === 'prev') {
            return <a>上一页</a>;
        } else if (type === 'next') {
            return <a>下一页</a>;
        }
        return originalElement;
    }

    render() {
            var data=[];
            this.props.data.map((item,index)=>{
                let clsName='';
                if(index==0){
                    clsName='active'
                }
                data.push(<li key={index} className={clsName}>{item.questionNumber+1}<span style={{display:"none",background:"#4AA0ED"}}>{item.id}</span></li>);
            })
        return (
            <div>
                 { <ul className="select-labels" style={{cursor: 'pointer',height:'20px'}} ref="boy" onClick={this.handle}>
                    {data}
                </ul> }
                    <div className="quest-title">
                    <span className="dot">{this.state.questionNumber+1}</span>
                    <label style={{marginLeft:"20px"}}>{this.state.questionDescription}</label>
                    <div className="line"></div>
                    <Button type="primary" className="storageButton" onClick={this.GoodAdvice} >好建议库</Button>
                    </div>
                  {this.state.list.map((item,index)=>{
                      return <AnswerOption data={item} key={index} style={{marginTop:"30px"}} search={this.search} type={this.state.type}  id={this.state.id}/>
                  })}
                {/*分页*/}
                {this.state.total ? <Pagination
                    className="pagination"
                    showSizeChanger
                    onShowSizeChange={this.onPageSizeChange}
                    onChange={this.onPageChange}
                    total={this.state.total}
                    itemRender={this.pageItemRender}/> : ''}
            </div>
            );
    }
}




