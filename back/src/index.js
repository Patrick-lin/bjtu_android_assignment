import 'babel-polyfill';
import express from 'express';
import bodyParser from 'body-parser';

import { loadDatabase, Users, Trainers, Classes } from './db';

import addAuthRoutes from './routes/auth';
import addTrainerRoutes from './routes/trainer';
import addClassRoutes from './routes/class';

import initDb from './initData';

const PORT = process.env.PORT || 3000;

const app = express();

app.use(bodyParser.urlencoded({
  extended: true
}));

addAuthRoutes(app);
addTrainerRoutes(app);
addClassRoutes(app);

loadDatabase().then(async () => {
  if (!await Classes.count()) {
    initDb();
  }
}).catch(err => {
  console.error(err);
});

app.listen(PORT, () => {
  console.log('Server listening to port 3000');
});
