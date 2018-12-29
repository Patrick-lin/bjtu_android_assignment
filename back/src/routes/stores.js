import { Stores, Carts, Products } from "../db";
import authMiddleware from "../authMiddleware";
import { find } from 'lodash';

export default (app) => {
  /**
   * @api {get} /stores Stores list
   * @apiVersion 0.0.0
   * @apiName GetStores
   * @apiGroup Store
   *
   * @apiUse UserAuth
   *
   * @apiParam {String[]} [ids] list of selected trainer ids, if not provided all trainers will be sent
   *
   * @apiExample {curl} example :
   *    curl -X GET 0:3000/stores -H "authorization: TOKEN user-auth-token"
   *
   *    // to get selection of stores
   *    curl -X GET "0:3000/stores?ids[]=storeId1" -H "authorization: TOKEN user-auth-token" --globoff
   *    curl -X GET "0:3000/stores?ids[]=storeId1&ids[]=trainerId2" -H "authorization: TOKEN user-auth-token" --globoff
   * @apiSuccess {Object[]} list of store
   * @apiSuccess {String} list._id
   * @apiSuccess {String} list.name
   * @apiSuccess {String} list.cover
   * @apiSuccess {String} list.description
   *
   * @apiSuccess {String} [error]
   */
  app.get('/stores', authMiddleware(), async (req, res) => {
    if (!req.user) {
      return res.send({
        list: [],
        error: 'not-authorized',
      });
    }

    const { ids } = req.query;
    return res.send({
      list: await Stores.find(ids ? { _id: { $in: isArray(ids) ? ids : [ids] } } : {}),
    });
  });

  app.post('/buy', authMiddleware(), async (req, res) => {
    const user = req.user;
    if (!req.user) {
      return res.send({
        products: [],
        error: 'not-authorized',
      });
    }

    await Carts.remove({ userId: user._id });
    await Carts.insert({ userId: user._id, products: [], total: 0 });
    const cart = await Carts.findOne({ userId: user._id });
    return res.send(cart);
  });

  app.get('/cart', authMiddleware(), async (req, res) => {
    if (!req.user) {
      return res.send({
        products: [],
        error: 'not-authorized',
      });
    }

    let cart = await Carts.findOne({ userId: req.user._id });
    if (!cart) {
      await Carts.insert({ userId: req.user._id, products: [], total: 0 });
      cart = await Carts.findOne({ userId: req.user._id });
    }
    return res.send(Object.assign({}, cart));
  });


  app.post('/product/:productId/add', authMiddleware(), async (req, res) => {
    const user = req.user;
    if (!user) {
      return res.send({ products: [] });
    }

    const { productId } = req.params; 

    let cart = await Carts.findOne({ userId: req.user._id });
    if (!cart) {
      await Carts.insert({ userId: req.user._id, products: [], total: 0 });
      cart = await Carts.findOne({ userId: req.user._id });
    }

    const product = await Products.findOne({ _id: productId });
    const cp = find(cart.products, { productId });
    if (cp) {
      cp.qty += 1;
      cp.total += product.price;
    } else {
      cart.products.push(Object.assign({}, product, {
        _id: `${user._id}-${productId}`,
        productId,
        qty: 1,
        total: product.price,
      }));
    }

    await Carts.update({ _id: cart._id }, cart);
    return res.send(cart);
  });
}