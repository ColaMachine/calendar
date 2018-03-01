/**
 * 版本升级管理列表
 * 文件名:index.js 方便通过目录import
 */
import React from 'react'//导入react
import {Button, Table, Modal, Pagination, notification} from 'antd';//ant design
import {Link} from 'react-router-dom'//react 路由
import util from '../../../util/formatTool' // util类 没有用到
import  Axios from 'axios';//ajax
import  QueryString from 'querystring';//拼接 参数
//import $http from '../../../util/http';//$http 没有用到
import {InvestStatus} from '../qestConfig';//配置项目

let {WAIT, OPEN, CLOSED} = InvestStatus;//配置的enum 表示了问卷的未启用 打开 关闭

export default class InvestigateList extends React.Component {
    constructor() {
        super();
        this.state = {
            list: [],//ajax 获取的数据
            total: 0//总数
        }
        this.onPageSizeChange = this.onPageSizeChange.bind(this);
        this.onPageChange = this.onPageChange.bind(this);

        this.pageNo = 1;
        this.pageSize = 10;

    }
    /**控件加载之前执行**/
    componentWillMount() {
        this.HeaderToken = {headers: {token: sessionStorage.token}};
    }
    /**加载完成**/
    componentDidMount() {
        this.search();
    }
    /**分页变动**/
    onPageSizeChange(current, size) {
        this.pageSize = size;
        this.search();
    }
      /**分页跳转**/
    onPageChange(page, pageSize) {
        this.pageNo = page;
        this.search();
    }

    //查询
    search() {
        let url = 'questionnaire/list';//问卷调查的url
        url = url + '?' + "source=shixun" + "&" + `pageNum=${this.pageNo}` + "&" + `pageSize=${this.pageSize}`;//凭借参数
        Axios.get(url, this.HeaderToken).then(({data}) => {//请求数据
            let {list = [], total = 0} = data.data;
            list.forEach((item, index) => {
                item.key = index + this.pageSize * (this.pageNo - 1) + 1;//拼接序号
            })
            this.setState({
                list, total//放入state中 修改state 重新渲染
            });
            console.log(list)//打印
        }).catch((err) => {
            console.error('获取列表失败:', err)//
        })
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
        const columns = [
            {
                title: '问卷名称',
                dataIndex: 'title',
            }, {
                title: '状态',
                dataIndex: 'status',
                render: (text) => {
                    return text == WAIT ? "未开始" : "" || text == OPEN ? "进行中" : "" || text == CLOSED ? "已结束" : text//所有的空字符串会被认为是false
                }
            }, {
                title: '参与人数',
                dataIndex: 'answerNumber',
            }, {
                title: '题目数量',
                dataIndex: 'questionNumber',
            }, {
                title: '统计分析',
                render: (text, record, index) => {
                    let span=<span>统计分析</span>//如果是在进行中的就是灰色的按钮
                    let p=<option disabled>统计分析</option>
                    /*if(text.status==1){
                           return <Link to={//
                                                `/index/investigateManager/statistics?id=${text.id}&title=${text.title}`
                                            }>{text.status==1?p:span}</Link>
                    }else{
                        return <span>统计分析</span>
                    }*/
                    return <Link to={//
                        `/index/investigateManager/statistics?id=${text.id}&title=${text.title}`
                    }>{text.status==1?p:span}</Link>//如果是状态1的表示还未开始 就用 option disabled来禁用掉 显示灰色
                }
            }, {
                title: '操作',
                render: (text, record, index) => {
                    let {status, id, title} = text;

                    let params = QueryString.stringify({[InvestStatus.KEY]:status, id, title});//将参数改写成 url 参数格式
                    return <Link to={
                        `/index/investigateManager/preview?${params}`
                    }>编辑</Link>//点击编辑会跳到预览页面
                }
            }];
        //渲染函数  最终的渲染效果  标题  按钮栏  列表table 分页
        return (
            <div className="right-management">
                <div className="page-title-me" style={{width: '100%'}}>调查问卷</div>
                <div className="form">
                    <Button type="primary" className="storageButton"
                    ><Link to="/index/investigateManager/create">创建问卷</Link></Button>
                </div>
                <div className="table">
                    <Table columns={columns} className="dev-table"
                           style={{backgroundColor: '#fff'}}
                           dataSource={this.state.list}
                           pagination={false}/>
                </div>
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

