import fs from 'fs';
import path from 'path';

export default (app) => {
  app.get('/videos', (req, res) => {
    return res.send({
      list: [
        {
          id: 'aoeu',
          title: 'test',
          thumbnail: 'https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=754226097,347722291&fm=15&gp=0.jpg',
        },
        {
          id: 'aoeuaoeu',
          title: 'test 2',
          thumbnail: 'https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=754226097,347722291&fm=15&gp=0.jpg',
        },
      ],
    });
  });

  app.get('/video/stream/:id', async (req, res) => {
    const filePath = path.join(__dirname, '../../videos/sample.mp4');
    console.log(filePath);

    try {
      const stat = await new Promise((resolve, reject) => fs.stat(filePath, (err, stats) => {
        if (err) {
          return reject(err);
        }
        return resolve(stats);
      }));
      const total = stat.size;

      if (req.headers['range']) {
        const range = req.headers.range;
        const parts = range.replace(/bytes=/, "").split("-");
        const partialStart = parts[0];
        const partialEnd = parts[1];
        const start = parseInt(partialStart, 10);
        const end = partialEnd ? parseInt(partialEnd, 10) : total - 1;
        
        const chunksize = (end - start)+1;
        console.log('RANGE: ' + start + ' - ' + end + ' = ' + chunksize);
        
        var file = fs.createReadStream(filePath, { start, end });
        res.writeHead(206, {
          'Content-Range': 'bytes ' + start + '-' + end + '/' + total,
          'Accept-Ranges': 'bytes',
          'Content-Length': chunksize, 'Content-Type': 'video/mp4',
        });
        file.pipe(res);
      } else {
        console.log('ALL: ' + total);
        res.writeHead(200, { 'Content-Length': total, 'Content-Type': 'video/mp4' });
        fs.createReadStream(filePath).pipe(res);
      }
    } catch (err) {
      console.log(err);
    }
  });
}