#import scrapy
import MySQLdb
from scrapy.spider import Spider
#from tutorial.items import DmozItem
import json
import chardet
from gl import SpiderPathUrl
import sys
class zhanqi(Spider):#scrapy.spiders.Spider
    reload(sys)
    sys.setdefaultencoding('utf-8')
    name = "zhanqi"
    allowed_domains = ["zhanqi.tv"]
    start_urls = [
        #"http://www.zhanqi.tv/games/dota2"
        #"http://www.zhanqi.tv/lives"
        
        "http://www.zhanqi.tv/api/static/live.hots/30-1.json"
    ]

    def parse(self, response):
        """hatype="dota2"
        vkey="zhanqi"
        root_path='//div[@class="live-list-tabc active"]/ul/li'
        #root_path='//div[@class="live-list-tabc tabc js-room-list-tabc"]/ul/li'
        title_path='.//span[@class="name"]/text()'
        href_path='.//a/@href'
        img_path='.//img/@src'
        author_path='.//span[@class="anchor anchor-to-cut dv"]/text()'
        audian_path='.//span[@class="dv"]/text()'
        url_prefix="http://www.zhanqi.tv"
        SpiderPathUrl.process(response,root_path,title_path,href_path,img_path,author_path,audian_path,url_prefix,hatype,vkey)"""
        resultJson= json.loads(response.body_as_unicode())  
        root_j="rooms"
        title_j="title"
        href_j="url"
        img_j="bpic"
        author_j="nickname"
        audian_j="online"
        vkey="id"
        url_prefix="http://www.zhanqi.tv"
        vType_j ="gameName"
        SpiderPathUrl.processJSON(resultJson["data"]["rooms"],title_j,href_j,img_j,author_j,audian_j,url_prefix,vType_j,vkey,"zhanqi","")