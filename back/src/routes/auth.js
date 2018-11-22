import { pick } from 'lodash';

import authMiddleware from '../authMiddleware';
import { Users } from '../db';

export default (app) => {
  /**
   * @api {post} /login Login
   * @apiName login
   * @apiGroup Auth
   *
   * @apiExample {curl} example :
   *    curl -X POST 0:3000/login -d "username=test" -d "password=test"
   *
   * @apiParam {String} username
   * @apiParam {String} password
   *
   * @apiSuccess {Boolean} success
   * @apiSuccess {String} message
   * @apiSuccess {String} token
   */
  app.post('/login', async (req, res) => {
    const { username, password } = req.body;
    const user = await Users.findOne({ username, password });
    if (!user) {
      return res.send({
        success: false,
        message: 'invalid-credentials',
      });
    }

    return res.send({
      success: true,
      message: 'login-success',
      token: user._id,
    });
  });

  /**
   * @api {post} /register Register
   * @apiName register
   * @apiGroup Auth
   *
   * @apiExample {curl} example :
   *    curl -X POST 0:3000/register -d "username=test" -d "password=test"
   *
   * @apiParam {String} username
   * @apiParam {String} password
   *
   * @apiSuccess {Boolean} success
   * @apiSuccess {String} message
   * @apiSuccess {String} token
   */
  app.post('/register', async (req, res) => {
    const { username, password } = req.body;

    const userWithSameUsername = await Users.findOne({ username });
    if (userWithSameUsername) {
      return res.send({
        success: false,
        message: 'username-already-used',
      });
    }

    const user = await Users.insert({ username, password });
    return res.send({
      success: true,
      message: 'register-success',
      token: user._id,
    });
  });

  /**
   * @api {get} /user/me Get my account
   * @apiVersion 0.0.0
   * @apiName Me
   * @apiGroup User
   *
   * @apiUse UserAuth
   */
  app.get('/user/me', authMiddleware(), async (req, res) => {
      if (!req.user) {
        return res.send({
          error: 'not-authorized',
        });
      }

      return res.send({
        user: pick(req.user, ['username']),
      });
  });
}
