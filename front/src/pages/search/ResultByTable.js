import React from "react"
import { ResultList } from '@appbaseio/reactivesearch'
import { Badge, Card } from 'react-bootstrap'
import Config from '../../config'

const ResultByTable = ({ item, key }) => {
    const windowOpen =  (item) => {
        window.open(`${Config.resourceApi}/${item.type}/${item.identifier}`);
    }

    const title = item.title
        ? item.title
        : item.description
            ? item.description
            : item.name

    const objCnt = item.dbXrefs.length;

    const groupBy = item.dbXrefs.reduce((result, current) => {

        //部署がprevにあるか
        const element = result.find(value => value.type === current.type);

        if (element) {
            //ある時（下記、初期データを操作）
            element.count++;
        } else {
            //無いとき（新規に初期データを作成）
            result.push({
                type: current.type,
                count: 1,
            })
        }
        return result;

    }, []);

    return (
        <ResultList
            key={key}
            style={{
                width: "100%",
                backgroundColor: "transparent"
            }}
            onClick={() => windowOpen(item)}
        >
            <Card
                style={{
                    width: "100%",
                    minHeight: 110
                }}
            >
                <Card.Body
                >
                    <div  style={{display: "flex", justifyContent:"space-between"}}>
                            <span style={{fontSize: 14, marginBottom: 10}}>
                                {item.identifier}
                            </span>
                        <span style={{fontSize: 14, marginBottom: 10}}>
                                {item.type}
                            </span>
                    </div>
                    <Card.Subtitle
                        style={{
                            fontSize: 14,
                            textOverflow: "ellipsis",
                            marginBottom: 5,
                            width: "100%",
                            overFlow: "hidden"
                        }}
                    >
                        {title}
                    </Card.Subtitle>
                    <div  style={
                        {display: "flex", justifyContent:"space-between", alignItems: "end"}}
                    >
                            <span>
                                <span
                                    style={{fontSize: 13}}
                                >This Object is related to {objCnt} Objects
                                </span>
                                {groupBy.map((g) => (
                                    <span
                                        style={{marginLeft: 5}}>
                                         <Badge
                                             variant="secondary"
                                         >
                                             {g.type} : {g.count}
                                         </Badge>
                                    </span>
                                ))}
                            </span>
                        <span style={{fontSize: 13}}>
                            {item.datePublished}
                        </span>
                    </div>
                </Card.Body>
            </Card>
        </ResultList>
    )
}

export default ResultByTable