package com.fc.projectboard.controller;

import com.fc.projectboard.dto.UserAccountDto;
import com.fc.projectboard.dto.request.ArticleCommentRequest;
import com.fc.projectboard.dto.security.BoardPrincipal;
import com.fc.projectboard.service.ArticleCommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@RequestMapping("/comments")
@Controller
public class ArticleCommentController {

    @Autowired
    private final ArticleCommentService articleCommentService;

    @PostMapping("/new")
    public String postNewArticleCommnet (
            @AuthenticationPrincipal BoardPrincipal principal,
            ArticleCommentRequest articleCommentRequest
    ) {
        articleCommentService.saveArticleComment(articleCommentRequest.toDto(principal.toDto()));

        return "redirect:/articles/" + articleCommentRequest.articleId();
    }

    @PostMapping("/{commentId}/delete")
    public String deleteArticleComment(
            @PathVariable Long commentId,
            @AuthenticationPrincipal BoardPrincipal principal,
            Long articleId) {

        articleCommentService.deleteArticleComment(commentId, principal.username());

        return "redirect:/articles/" + articleId;
    }
}
