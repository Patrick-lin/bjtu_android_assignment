import { isArray } from 'lodash'

import { Trainers } from '../db';
import authMiddleware from '../authMiddleware';

export default (app) => {
  /**
   * @api {get} /trainers Trainer list
   * @apiVersion 0.0.0
   * @apiName GetTrainers
   * @apiGroup trainer
   *
   * @apiUse UserAuth
   *
   * @apiParam {String[]} [ids] list of selected trainer ids, if not provided all trainers will be sent
   *
   * @apiExample {curl} example :
   *    curl -X GET 0:3000/trainers -H "authorization: TOKEN user-auth-token"
   *
   *    // to get selection of trainers
   *    curl -X GET "0:3000/trainers?ids[]=trainerId1" -H "authorization: TOKEN user-auth-token" --globoff
   *    curl -X GET "0:3000/trainers?ids[]=trainerId1&ids[]=trainerId2" -H "authorization: TOKEN user-auth-token" --globoff
   * @apiSuccess {Object[]} list list of trainers
   * @apiSuccess {String} list.id
   * @apiSuccess {String} list.firstName
   * @apiSuccess {String} list.lastName
   * @apiSuccess {Number} list.age
   * @apiSuccess {String} list.cover
   *
   * @apiSuccess {String} [error]
   */
  app.get('/trainers', authMiddleware(), async (req, res) => {
    if (!req.user) {
      return res.send({
        list: [],
        error: 'not-authorized',
      });
    }
    console.log(req.query);
    const { ids } = req.query;
    return res.send({
      list: await Trainers.find(ids ? { _id: { $in: isArray(ids) ? ids : [ids] } } : {}),
    });
  });

  /**
   * @api {get} /trainer/:id Get trainer
   * @apiVersion 0.0.0
   * @apiName GetTrainer
   * @apiGroup trainer
   *
   * @apiParam {String} id trainer's id
   *
   * @apiSuccess {Object} trainer
   * @apiSuccess {String} trainer.id
   * @apiSuccess {String} trainer.firstName
   * @apiSuccess {String} trainer.lastName
   * @apiSuccess {Number} trainer.age
   * @apiSuccess {String} trainer.cover
   * @apiSuccess {String} [error]
   */
  app.get('/trainer/:id', authMiddleware(), async (req, res) => {
    if (!req.user) {
      return res.send({ error: 'not-authorized' });
    }

    return res.send({
      trainer: await Trainers.findOne({ _id: req.params.id }),
    });
  });
}
