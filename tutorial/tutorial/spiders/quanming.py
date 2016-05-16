#import scrapy
import MySQLdb
from scrapy.spider import Spider
#from tutorial.items import DmozItem
import json
import chardet
from gl import SpiderPathUrl
import sys
class quanming(Spider):#scrapy.spiders.Spider
    reload(sys)
    sys.setdefaultencoding('utf-8')
    name = "quanming"
    allowed_domains = ["quanmin.tv"]
    start_urls = [
        #"http://www.quanmin.tv/json/categories/dota2/list.json?t=24362804"
        #"http://www.quanmin.tv/game/all"
        "http://www.quanmin.tv/json/play/list.json?t=24372728",
        "http://www.quanmin.tv/json/categories/beauty/list.json?t=24386703"
    ]

    def parse(self, response):
        resultJson= json.loads(response.body_as_unicode())  
        
        title_j="title"
        href_j="slug"
        img_j="thumb"
        author_j="nick"
        audian_j="view"
        vkey="slug"#uid 
        vType_j="category_name"
        url_prefix="http://www.quanmin.tv/v/"
      
        SpiderPathUrl.processJSON(resultJson["data"],title_j,href_j,img_j,author_j,audian_j,url_prefix,vType_j,vkey,"quanming","")
        
