/**
 * Created by songbo on 2017/8/22.
 */
import React from 'react'
import {Menus} from '../../util/menu'
import {Menu, Icon, Switch} from 'antd';
import {Route, Link, HashRouter, Redirect} from 'react-router-dom'
import DeviceManagement from '../deviceManagement/list'
import DeviceStorage from '../deviceManagement/storage'
import userManagement from '../userManagement'
import Blacklist from '../blacklist/Blacklist.jsx';
import DisabledUserManagement from '../disableduser';
import liveBroadcast from '../liveVideoMgr/list'
import VersionManager from '../versionManager'
import investigateManager from '../investigateManager'
import Logo from  '../../images/icloud.png'
import $http from '../../util/http'

import TopHeader from './header'
import 'src/css/animations.css'

const SubMenu = Menu.SubMenu;
//菜单以及内容展示页面
export default class mainPage extends React.Component {
    constructor() {
        super()
        this.state = {
            rightTitle: 'blank',
            current: '1',
        }
        this.rightTitles = {};
    }

    componentWillMount() {
        /*if (!sessionStorage.token) {
            window.location.hash = "/login";
            return;
        }*/
    }

    componentDidMount() {
        let activeMenu = () => {
            let hash = window.location.hash;
            if (!hash)
                return;

            let ar = hash.split('/')
            if (ar.length < 2)
                return;
            let key = ar[ar.length - 1];
            if (!key)
                return;

            this.setState({
                current: key,
                rightTitle: this.rightTitles[ar[2]]
            });
        }
        activeMenu();
    }

    menuClick(e) {

        this.setState({
            rightTitle: this.rightTitles[e.key],
            current: e.key,
        });
    }

    render() {
        const Home = (props) => {
            return (
                <div className="index-home">

                </div>)
        }

        const Video = (props) => {
            return <h1>摄像机页面正在开发中......Video</h1>
        }

        // 从menus的成员递归组装子菜单
        const getMenuShop = (subMenus, level) => {
            const getAvatar = (item, thisLevel) => {
                if (thisLevel > 1) {
                    console.log('是子菜单:', item.name)
                    return <span/>;
                }

                //菜单图标在这里设置
                return <span className={item.icon}>{item.name}</span>;
            }

            //level:通过样式控制递归菜单缩进量
            const getMenus = (item, level) => {
                let children = item.children;
                if (children && children.length > 0) {
                    return ( <SubMenu key={item.url} title={getAvatar(item, level)} className={`menu-${level}nd`}>
                        {children.map((it, index) => {
                            return getMenus(it, level + 1);
                        })}
                    </SubMenu>);
                }


                return (<Menu.Item key={item.url} className={`menu-${level}nd`}>
                    <div className="arrow-frame">
                        <span className="bright-block"></span>
                        <span className="arrow-blk"> </span>
                        <Link to={`/index/${item.url}`}>

                            {(this.rightTitles[item.url] = item.name) && level == 1 ?
                                <span className={['icon',item.icon].join(" ")}/>: null} {item.name}
                        </Link>
                    </div>
                </Menu.Item>);
            }
            return getMenus(subMenus, 1);
        }

        return (
            <div className="containerAll">




                        <HashRouter>
                            <div className="subRouter">

                                <Route path="/index/investigateManager" component={investigateManager}/>
                            </div>
                        </HashRouter>


            </div>
        );
    }
}
