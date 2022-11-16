INSERT INTO `product_category` (`name`, `description`, `image_url`)
VALUES ('탁주', '맛있는 막걸리는 다 있어요', 'https://d38cxpfv0ljg7q.cloudfront.net/assets/icon-takju.png'),
       ('약*청주', '맑고 깨끗한 술의 원조', 'https://d38cxpfv0ljg7q.cloudfront.net/assets/icon-yakchungju.png'),
       ('과실주', '우리, 와인은 몰라도 분위기는 알잖아요', 'https://d38cxpfv0ljg7q.cloudfront.net/assets/icon-wine.png'),
       ('증류주', '소주도 취향 타는 거 알고 계셨어요?', 'https://d38cxpfv0ljg7q.cloudfront.net/assets/icon-soju.png');


INSERT INTO `product` (`product_category_id`, `name`, `price`, `image_url`, `description`, `abv`, `capacity`)
VALUES ('1', '백련 미스티 살균 막걸리', '4500', 'https://www.sooldamhwa.com/images/common/mainLogo.png', '연꽃이 들어간 살균 막걸리', '7.0', '375'),
       ('1', '구름을 벗삼아', '20000', 'https://www.sooldamhwa.com/images/common/mainLogo.png', '구름처럼 부드럽고 달콤한 막걸리', '6.0', '500'),
       ('2', '토박이 한산 소곡주', '11000', 'https://www.sooldamhwa.com/images/common/mainLogo.png', '한 번 마시면 멈출 수 없는 맛', '16.0', '500');