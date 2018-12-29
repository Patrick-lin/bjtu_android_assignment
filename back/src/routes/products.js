import { Products } from '../db';

export default (app) => {
  app.get('/products/:storeId/list', async (req, res) => {
    const { storeId } = req.params;

    res.send({
      list: await Products.find({ storeId }),
    });
  });
  return app;
}