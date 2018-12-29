import Datastore from '@shantry/nedb';

export const Settings = new Datastore({
  filename: './db/settings',
});

export const Users = new Datastore({
  filename: './db/users',
});
export const Classes = new Datastore({
  filename: './db/classes',
});
export const Trainers = new Datastore({
  filename: './db/trainers',
});

export const News = new Datastore({
  filename: './db/news',
});

export const Products = new Datastore({
  filename: './db/products',
});

export const Stores = new Datastore({
  filename: './db/stores',
});
export const Carts = new Datastore({
  filename: './db/cart',
});

export const loadDatabase = () => {
  const loadDb = db => new Promise(resolve => db.loadDatabase(resolve));
  return Promise.all(
    [Users, Classes, Trainers, News, Settings, Products, Stores, Carts].map(loadDb)
  );
};
