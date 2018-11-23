import { take, last, isNumber } from 'lodash';
import { News } from '../db';

export default (app) => {
  /**
   * @api {get} /news Get latest news
   * @apiVersion 0.0.0
   * @apiName GetNews
   * @apiGroup News
   *
   * @apiParam {Number} [limit=20] number of items to fetch
   * @apiParam {String} [cursor] cursor from which to start
   *
   * @apiExample {curl} example:
   *    curl -X GET "0:3000/news"
   *    curl -X GET "0:3000/news?limit=25"
   *    curl -X GET "0:3000/news?limit=119&cursor=1519798794005"
   *
   * @apiSuccess {Object[]} list list of news
   * @apiSuccess {String} list.id
   * @apiSuccess {String} list.title
   * @apiSuccess {String} list.tagLine
   * @apiSuccess {String} list.text
   * @apiSuccess {Date} list.date
   * @apiSuccess {String} list.cover
   *
   * @apiSuccess {String} cursor cursor to fetch next items
   */
  app.get('/news', async (req, res) => {
    const { limit = 20, cursor } = req.query;

    const query = {}
    if (cursor) {
      query.date = { $lt: new Date(cursor) };
    }
    let news = await News.find(query).sort({ date: -1 });

    let left = false;
    if (news.length > limit) {
      left = true;
      news = take(news, limit);
    }

    return res.send({
      list: news,
      cursor: left ? last(news).date.toString() : null,
    });
  });
}
