import { Classes } from '../db';
import authMiddleware from '../authMiddleware';

export default (app) => {
  /**
   * @apiDefine UserAuth Title
   * Description
   * @apiHeader {String} authorization user auth token
   * @apiHeaderExample {json} example:
   *  {
   *    "authorization": "TOKEN user-auth-token"
   *  }
   */

  /**
   * @api {get} /classes Class list
   * @apiVersion 0.0.0
   * @apiName GetClasses
   * @apiGroup class
   *
   * @apiUse UserAuth
   *
   * @apiExample {curl} example :
   *    curl -X GET 0:3000/classes -H "authorization: TOKEN user-auth-token"
   *
   * @apiSuccess {Object[]} list list of class
   * @apiSuccess {String} list.id
   * @apiSuccess {String} list.type type of class (may be used to specialise ui)
   * @apiSuccess {String} list.title class title
   * @apiSuccess {String} list.tagLine class tag line
   * @apiSuccess {String} list.cover class cover url
   * @apiSuccess {Number} list.maxPlaces number of max seat
   * @apiSuccess {Number} list.takenPlaces number of taken seat
   * @apiSuccess {String[]} list.availableTrainerIds list of available trainer ids
   * @apiSuccess {Boolean} list.choosableTrainer if trainer may be chooseable
   * @apiSuccess {Number} list.nbChoosableTrainer number of chooseable trainer
   *
   * @apiSuccess {String} [error]
   */
  app.get('/classes', authMiddleware(), async (req, res) => {
    if (!req.user) {
      return res.send({
        list: [],
        error: 'not-authorized',
      });
    }
    return res.send({
      list: await Classes.find({})
    });
  });

  /**
   * @api {get} /class/:id Get class
   * @apiVersion 0.0.0
   * @apiName GetClass
   * @apiGroup class
   *
   * @apiParam {String} id class's id
   *
   * @apiSuccess {Object} class
   * @apiSuccess {String} class.id
   * @apiSuccess {String} class.type type of class (may be used to specialise ui)
   * @apiSuccess {String} class.title class title
   * @apiSuccess {String} class.tagLine class tag line
   * @apiSuccess {String} class.cover class cover url
   * @apiSuccess {Number} class.maxPlaces number of max seat
   * @apiSuccess {Number} class.takenPlaces number of taken seat
   * @apiSuccess {String[]} class.availableTrainerIds list of available trainer ids
   * @apiSuccess {Boolean} class.choosableTrainer if trainer may be chooseable
   * @apiSuccess {Number} class.nbChoosableTrainer number of chooseable trainer
   * @apiSuccess {String} [error]
   */
  app.get('/class/:id', authMiddleware(), async (req, res) => {
    if (!req.user) {
      return res.send({ error: 'not-authorized' });
    }

    return {
      class: await Classes.findOne({ _id: req.params.id }),
    };
  });
}
