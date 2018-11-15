import faker from 'faker';

import { Trainers, Classes } from './db';
import { arrayElements } from './utils';

const createTrainer = () => ({
  firstName: faker.name.firstName(),
  lastName: faker.name.lastName(),
  age: faker.random.number({ min: 20, max: 35 }),
  cover: faker.random.arrayElement([
    "https://www.randomaddressgenerator.com/media/face/male36.jpg",
    "https://www.randomaddressgenerator.com/media/face/male67.jpg",
    "https://www.randomaddressgenerator.com/media/face/female72.jpg",
    "https://www.randomaddressgenerator.com/media/face/male44.jpg",
    "https://www.randomaddressgenerator.com/media/face/female40.jpg",
    "https://www.randomaddressgenerator.com/media/face/male50.jpg",
    "https://www.randomaddressgenerator.com/media/face/male32.jpg",
    "https://www.randomaddressgenerator.com/media/face/female82.jpg",
  ]),
});

const initTrainers = () => Trainers.insert([
  createTrainer(),
  createTrainer(),
  createTrainer(),
  createTrainer(),
  createTrainer(),
  createTrainer(),
  createTrainer(),
  createTrainer(),
  createTrainer(),
  createTrainer(),
  createTrainer(),
  createTrainer(),
]);

const createClass = ({
  type,
  title,
  availableTrainerIds,

  choosableTrainer = false,
  nbChoosableTrainer = 1,
  maxPlaces = 1,
  takenPlaces = 0,
  tagLine = faker.lorem.sentence(3),
  cover = 'https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=2720094953,3884838007&fm=15&gp=0.jpg',
}) => ({
  type,
  title,
  tagLine,

  availableTrainerIds,
  choosableTrainer,
  nbChoosableTrainer,

  maxPlaces,
  takenPlaces,

  cover,
});

const PRIVATE_CLASS = 'privateClass';
const GROUP_CLASS = 'groupClass';

const toIds = elem => elem._id

const initClasses = async () => {
  const trainers = await Trainers.find();
  return Classes.insert([
    createClass({
      type: PRIVATE_CLASS,
      title: 'Private training',
      availableTrainerIds: trainers.map(toIds),
      choosableTrainer: true,
    }),
    createClass({
      type: GROUP_CLASS,
      title: 'Cardio training',
      availableTrainerIds: trainers.map(toIds),
      choosableTrainer: true,
      nbChoosableTrainer: 2,
      maxPlaces: 50,
      takenPlaces: 6,
    }),
    createClass({
      type: GROUP_CLASS,
      title: 'Yoga training',
      availableTrainerIds: arrayElements(trainers, 2, toIds),
      maxPlaces: 50,
      takenPlaces: 12,
    }),
    createClass({
      type: GROUP_CLASS,
      title: 'Dance training',
      availableTrainerIds: arrayElements(trainers, 1, toIds),
      maxPlaces: 50,
      takenPlaces: 50,
    }),
  ]);
}

export default async () => {
  await initTrainers();
  await initClasses();
}
