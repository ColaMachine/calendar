#import scrapy
import MySQLdb
from scrapy.spider import Spider
#from tutorial.items import DmozItem
import json
import chardet
from gl import SpiderPathUrl
import sys
class panda(Spider):#scrapy.spiders.Spider
    reload(sys)
    sys.setdefaultencoding('utf-8')
    name = "panda"
    allowed_domains = ["panda.tv"]
    start_urls = [

        #"http://www.panda.tv/cate/dota2"
        #"http://www.panda.tv/all"
        "http://www.panda.tv/live_lists?status=2&order=person_num&pageno=1&pagenum=120"
    ]

    def parse(self, response):
        #hatype="dota2"
        #vkey="panda"
        #root_path='//ul[@class="video-list clearfix"]/li'
        


        #root_path='//div[@class="list-container"]/ul/li'
        """title_path='.//div[@class="video-title"]/text()'
        href_path='.//a/@href'
        img_path='.//img/@data-original'
        author_path='.//span[@class="video-nickname"]/text()'
        audian_path='.//span[@class="video-number"]/text()'
        url_prefix="http://www.panda.tv"
        SpiderPathUrl.process(response,root_path,title_path,href_path,img_path,author_path,audian_path,url_prefix,hatype,vkey)"""

        resultJson= json.loads(response.body_as_unicode())  
        
        title_j="name"
        href_j="id"
        img_j="pictures.img"
        author_j="userinfo.nickName"
        audian_j="person_num"
        vkey="id"
        url_prefix="http://www.panda.tv/"
        vType_j ="classification.cname"
        SpiderPathUrl.processJSON(resultJson["data"]["items"],title_j,href_j,img_j,author_j,audian_j,url_prefix,vType_j,vkey,"panda","")
        