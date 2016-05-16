import MySQLdb
import sys;
import os
import scrapy
sys.path.append("spiders")
from  douyu import douyu
from  douyuStar import douyuStar
from longzhu import longzhu
from zhanqi import zhanqi
from quanming import quanming
from panda import panda
from huya import huya

from scrapy.crawler import CrawlerProcess


from scrapy.utils.project import get_project_settings



import json
import chardet
from gl import SpiderPathUrl
import sys

# class MySpider(scrapy.Spider):
#     name = 'stackoverflow'
#     start_urls = ['http://stackoverflow.com/questions?sort=votes']

#     def parse(self, response):
#         for href in response.css('.question-summary h3 a::attr(href)'):
#             full_url = response.urljoin(href.extract())
#             yield scrapy.Request(full_url, callback=self.parse_question)

#     def parse_question(self, response):
#         yield {
#             'title': response.css('h1 a::text').extract()[0],
#             'votes': response.css('.question .vote-count-post::text').extract()[0],
#             'body': response.css('.question .post-text').extract()[0],
#             'tags': response.css('.question .post-tag::text').extract(),
#             'link': response.url,
#         }
process = CrawlerProcess(get_project_settings())
SpiderPathUrl.opendb();
SpiderPathUrl.clearDb();
# SpiderPathUrl.commitdb();
# SpiderPathUrl.opendb();
#print("hello \n")
#process.crawl(douyuStar)
process.crawl(huya)

process.crawl(douyu)
process.crawl(panda)
process.crawl(zhanqi)
process.crawl(quanming)
process.crawl(longzhu)

process.crawl(quanming)
process.start()
SpiderPathUrl.commitdb();


    