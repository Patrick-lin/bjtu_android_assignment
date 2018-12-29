import 'babel-polyfill';
import express from 'express';
import bodyParser from 'body-parser';
import morgan from 'morgan';
import cors from 'cors';

import { loadDatabase, Users, Trainers, Classes } from './db';

import addAuthRoutes from './routes/auth';
import addTrainerRoutes from './routes/trainer';
import addClassRoutes from './routes/class';
import addNewsRoute from './routes/news';
import addVideoRoute from './routes/video';
import addStoreRoute from './routes/stores';
import addProductsRoute from './routes/products';

import initDb from './initData';

const PORT = process.env.PORT || 3000;

const app = express();

app.use(cors());
app.use(morgan('common'));
app.use(bodyParser.urlencoded({
  extended: true
}));
app.use(bodyParser.json());

addAuthRoutes(app);
addTrainerRoutes(app);
addClassRoutes(app);
addNewsRoute(app);
addVideoRoute(app);
addStoreRoute(app);
addProductsRoute(app);

loadDatabase().then(async () => {
  initDb();
}).catch(err => {
  console.error(err);
});

app.listen(PORT, () => {
  console.log('Server listening to port 3000');
});
