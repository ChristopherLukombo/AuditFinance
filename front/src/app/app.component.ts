import { Component, OnInit } from '@angular/core';
import { Action } from './models/action';
import { ActionService } from './services/action-service/action.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit {
  price = 25;
  response = {
    today: {
      date: '12-10-2022',
      opening: 25,
      highest: 30,
      lowest: 5,
      volume: 10
    },

    history: [
      {
        date: '12-12-2022',
        opening: 25,
        highest: 30,
        lowest: 5,
        volume: 10
      }
    ]
  };

  MOCK = {
    'Meta Data': {
      '1. Information': 'Daily...',
      '2. Symbol': 'IBM',
      '3. Last Refreshed': '2022-12-02',
      '4. Output Size': 'Compact',
      '5. Time Zome': 'US/Eastern'
    },
    'Time Series (Daily)': {
      '2022-12-02': {
        '1. open': '148.13',
        '2. high': '149.16',
        '3. low': '147.67',
        '4. close': '148.67',
        '5. adjusted close': '148.67',
        '6. volume': '2899995',
        '7. dividend amount': '0.0000',
        '8. split coefficient': '1.0'
      }
    }
  };

  query = '';

  action: Action | null = null;

  constructor(private actionService: ActionService) {}

  ngOnInit(): void {
    this.search();
  }

  search() {
    // MA MISSION: "Donne moi les routes (herbergées)"

    // 1. Lorsque l'utilisateur fait une recherche
    // fetch(`https://www.sonapi.com/stockdujour?stock=${this.query}`)

    // 2. Appeler le back avec la "recherche (IBM)"
    // 3. Recuperer les données
    // 4. les assigner à mes attributs

    this.actionService.consultation('stock', 'date').subscribe((response) => {
      this.action = response.body;
    });
    this.response = {
      today: {
        date: '12-10-2022',
        opening: 1000,
        highest: 2000,
        lowest: 1,
        volume: 5
      },

      history: [
        {
          date: '12-12-2022',
          opening: 25,
          highest: 30,
          lowest: 5,
          volume: 10
        }
      ]
    };
  }
}
